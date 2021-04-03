package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.annotation.IntRange
import androidx.annotation.MainThread
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.dialogs.AddCardDialog
import com.dowgalolya.gwenthelper.dialogs.EditCardDialog
import com.dowgalolya.gwenthelper.entities.*
import com.dowgalolya.gwenthelper.enums.CardsRowType
import com.dowgalolya.gwenthelper.enums.Player
import com.dowgalolya.gwenthelper.enums.Winner
import com.dowgalolya.gwenthelper.extensions.notifyDataChanged
import com.dowgalolya.gwenthelper.fragments.GameFragmentDirections
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.repositories.GwentRepository
import com.dowgalolya.gwenthelper.widgets.WeatherView
import kotlinx.coroutines.launch
import java.util.*

class GameViewModel(
    application: Application,
    private val gwentRepository: GwentRepository,
    cardRowAdapterFactory: CardRowAdapter.Factory = CardRowAdapter.Factory()
) : BaseViewModel(application),
    AddCardDialog.OnCardAddListener,
    WeatherView.OnWeatherChangeListener,
    CardRowAdapter.Callback,
    EditCardDialog.OnCardEditListener {

    companion object {
        const val CARD_ROW = "CARD_ROW"
        const val CARD = "CARD"
    }

    object CustomViewAction {
        const val SHOW_ADD_CARD_DIALOG = "SHOW_ADD_CARD_DIALOG"
        const val SHOW_CONFIG_CARD_DIALOG = "SHOW_CONFIG_CARD_DIALOG"
        const val SHOW_EDIT_CARD_DIALOG = "SHOW_EDIT_CARD_DIALOG"
    }

    private val _gameData = MutableLiveData(
        GameData(
            firstPlayerData = PlayerData(),
            secondPlayerData = PlayerData()
        )
    )
    val gameData: LiveData<GameData> = _gameData

    private val _selectedPlayer = MutableLiveData(Player.FIRST)
    val selectedPlayer: LiveData<Player> = _selectedPlayer

    private val _selectedPlayerData = MediatorLiveData<PlayerData>().apply {
        value = _gameData.value!!.firstPlayerData//TODO: check it
        Observer<Any?> {
            value = when (_selectedPlayer.value!!) {
                Player.FIRST -> _gameData.value!!.firstPlayerData
                Player.SECOND -> _gameData.value!!.secondPlayerData
            }
            rowAdapters.forEach { (type, adapter) ->
                adapter.row = value!!.cardsRows.getValue(type)
            }
        }.also {
            addSource(_gameData, it)
            addSource(_selectedPlayer, it)
        }
    }
    val selectedPlayerData: LiveData<PlayerData> = _selectedPlayerData


    val rowAdapters: Map<CardsRowType, CardRowAdapter> = _selectedPlayerData.value!!.cardsRows
        .map { it.key to cardRowAdapterFactory.create(it.value, this) }
        .toMap()

    @IntRange(from = 0, to = 3)
    private var roundCounter: Int = 0

    private val roundsData: RoundsData = RoundsData()

    override fun onCardAdd(cardsRowType: CardsRowType, card: Card) {

        _selectedPlayerData.value!!.let { playerData ->
            playerData.cardsRows.getValue(cardsRowType).apply {
                cards += card
            }
        }
        _gameData.notifyDataChanged()
    }

    @MainThread
    fun onHornChecked(cardsRowType: CardsRowType, isChecked: Boolean) {
        _selectedPlayerData.value!!.cardsRows.getValue(cardsRowType).horn = isChecked
        _gameData.notifyDataChanged()

    }

    fun onUserClicked(player: Player) {
        _selectedPlayer.value = player
        _gameData.notifyDataChanged()
    }

    fun init(player1Name: String, player2Name: String, player1Pic : String, player2Pic : String) {
        _gameData.value?.firstPlayerData?.name = player1Name
        _gameData.value?.secondPlayerData?.name = player2Name
        _gameData.value?.firstPlayerData?.pic = player1Pic
        _gameData.value?.secondPlayerData?.pic = player2Pic

    }

    override fun onWeatherChange(cardsRowType: CardsRowType, weather: Boolean) {
        _gameData.value!!.apply {
            firstPlayerData.cardsRows.getValue(cardsRowType).badWeather = weather
            secondPlayerData.cardsRows.getValue(cardsRowType).badWeather = weather
        }
        _gameData.notifyDataChanged()
    }

    override fun onLongClick(row: CardsRow, card: Card) {
        _viewAction.value = ViewAction.Custom(CustomViewAction.SHOW_CONFIG_CARD_DIALOG)
            .putArg(CARD_ROW, row)
            .putArg(CARD, card)
    }

    @MainThread
    override fun onClick(row: CardsRowType) {
        _viewAction.value =
            ViewAction.Custom(CustomViewAction.SHOW_ADD_CARD_DIALOG).putArg(CARD_ROW, row)

    }

    @MainThread
    fun onEditClicked(row: CardsRow, card: Card) {
        _viewAction.value = ViewAction.Custom(CustomViewAction.SHOW_EDIT_CARD_DIALOG)
            .putArg(CARD_ROW, row)
            .putArg(CARD, card)
    }

    @MainThread
    fun onDeleteClicked(row: CardsRow, card: Card) {
        row.apply {
            cards -= card
        }
        _gameData.notifyDataChanged()
    }

    fun onRoundEnds() {
        roundCounter += 1
        with(_gameData.value!!) {
            when (winner) {
                Winner.FIRST -> secondPlayerData.minusLive()
                Winner.SECOND -> firstPlayerData.minusLive()
                Winner.TIE -> {
                    secondPlayerData.minusLive()
                    firstPlayerData.minusLive()
                }
            }
            when (roundCounter) {
                1 -> {
                    roundsData.firstRoundFirstPlayerPoints = firstPlayerData.totalPoints
                    roundsData.firstRoundSecondPlayerPoints = secondPlayerData.totalPoints
                }
                2 -> {
                    roundsData.secondRoundFirstPlayerPoints = firstPlayerData.totalPoints
                    roundsData.secondRoundSecondPlayerPoints = secondPlayerData.totalPoints
                }
                3 -> {
                    roundsData.thirdRoundFirstPlayerPoints = firstPlayerData.totalPoints
                    roundsData.thirdRoundSecondPlayerPoints = secondPlayerData.totalPoints
                }
            }
            when {
                firstPlayerData.lives == 0 && secondPlayerData.lives == 0 -> onGameOver(Winner.TIE)
                firstPlayerData.lives == 0 -> onGameOver(Winner.SECOND)
                secondPlayerData.lives == 0 -> onGameOver(Winner.FIRST)
                else -> {
                    firstPlayerData.cardsRows.forEach { (_, row) -> row.cards = emptyList() }
                    secondPlayerData.cardsRows.forEach { (_, row) -> row.cards = emptyList() }
                    _gameData.notifyDataChanged()
                }
            }
        }
    }

    private fun onGameOver(winner: Winner) {

        saveGame(winner)

        val direction = GameFragmentDirections.actionGameFragmentToGameResultFragment().apply {
            user1 = _gameData.value!!.firstPlayerData.name
            user2 = _gameData.value!!.secondPlayerData.name
            user1Photo = _gameData.value!!.firstPlayerData.pic
            user2Photo = _gameData.value!!.secondPlayerData.pic
            user1TotalPoints = roundsData.firstPlayerTotalPoints.toString()
            user2TotalPoints = roundsData.secondPlayerTotalPoints.toString()
            this.winner = winner.name
        }

        _viewAction.value = ViewAction.NavigateWithDirection(direction)
    }

    @MainThread
    private fun saveGame(winner: Winner) {
        val gameScore = _gameData.value!!.let {
            GameScore(
                date = Calendar.getInstance().time,
                firstPlayer = it.firstPlayerData.name,
                secondPlayer = it.secondPlayerData.name,
                winner = winner.name,
                firstRoundFirstPlayerPoints = roundsData.firstRoundFirstPlayerPoints,
                secondRoundFirstPlayerPoints = roundsData.secondRoundFirstPlayerPoints,
                thirdRoundFirstPlayerPoints = roundsData.thirdRoundFirstPlayerPoints,
                firstRoundSecondPlayerPoints = roundsData.firstRoundSecondPlayerPoints,
                secondRoundSecondPlayerPoints = roundsData.secondRoundSecondPlayerPoints,
                thirdRoundSecondPlayerPoints = roundsData.thirdRoundSecondPlayerPoints
            )
        }
        viewModelScope.launch {
            gwentRepository.addGame(gameScore)
        }
    }

    override fun onCardEdit(row: CardsRow, card: Card) {
        row.apply {
            cards = cards.filterNot { it.cardId == card.cardId } + card
        }
        _gameData.notifyDataChanged()
    }
}
