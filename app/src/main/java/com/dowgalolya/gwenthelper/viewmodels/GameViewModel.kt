package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.annotation.IntRange
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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
import com.dowgalolya.gwenthelper.livedata.SingleLiveEvent
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.repositories.GwentRepository
import com.dowgalolya.gwenthelper.widgets.WeatherView
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
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
        const val REVIEW_INFO = "REVIEW_INFO"
        const val REVIEW_MANAGER = "REVIEW_MANAGER"
    }

    object CustomViewAction {
        const val SHOW_ADD_CARD_DIALOG = "SHOW_ADD_CARD_DIALOG"
        const val SHOW_CONFIG_CARD_DIALOG = "SHOW_CONFIG_CARD_DIALOG"
        const val SHOW_EDIT_CARD_DIALOG = "SHOW_EDIT_CARD_DIALOG"
        const val FINISH_GAME = "FINISH_GAME"
        const val LAUNCH_REVIEW_AND_FINISH_GAME = "LAUNCH_REVIEW_AND_FINISH_GAME"
    }

    private val _gameData = MutableLiveData<GameData>().apply {
        value = GameData(
            firstPlayerData = PlayerData(),
            secondPlayerData = PlayerData()
        )
    }
    val gameData: LiveData<GameData> = _gameData

    private val _selectedPlayer = MutableLiveData<Player>().apply {
        value = Player.FIRST //TODO: check it
    }
    val selectedPlayer: LiveData<Player> = _selectedPlayer

    private val _selectedPlayerData = MediatorLiveData<PlayerData>().apply {
        value = _gameData.value!!.firstPlayerData//TODO: check it
        val observer: Observer<Any?> = Observer {
            value = when (_selectedPlayer.value!!) {
                Player.FIRST -> _gameData.value!!.firstPlayerData
                Player.SECOND -> _gameData.value!!.secondPlayerData
            }
            rowAdapters.forEach { (type, adapter) ->
                adapter.row = value!!.cardsRows.getValue(type)
            }
        }
        addSource(_gameData, observer)
        addSource(_selectedPlayer, observer)
    }
    val selectedPlayerData: LiveData<PlayerData> = _selectedPlayerData

    private val _gameOver = SingleLiveEvent<Winner>()
    val gameOver: LiveData<Winner> = _gameOver

    val rowAdapters: Map<CardsRowType, CardRowAdapter> = _selectedPlayerData.value!!.cardsRows
        .map { it.key to cardRowAdapterFactory.create(it.value, this) }
        .toMap()

    @IntRange(from = 0, to = 3)
    private var roundCounter : Int = 0

    private val roundsData : RoundsData = RoundsData()


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
        _selectedPlayerData.value!!.let { playerData ->
            playerData.cardsRows.getValue(cardsRowType).horn = isChecked
        }
        _gameData.notifyDataChanged()

    }

    fun onUserClicked(player: Player) {
        _selectedPlayer.value = player
        _gameData.notifyDataChanged()
    }

    fun init(player1Name: String, player2Name: String) {
        _gameData.value?.firstPlayerData?.name = player1Name
        _gameData.value?.secondPlayerData?.name = player2Name
    }

    override fun onWeatherChange(cardsRowType: CardsRowType, weather: Boolean) {
        _gameData.value!!.let {
            it.firstPlayerData.cardsRows.getValue(cardsRowType).badWeather = weather
            it.secondPlayerData.cardsRows.getValue(cardsRowType).badWeather = weather
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
        when (_gameData.value?.winner) {
            Winner.FIRST -> _gameData.value!!.secondPlayerData.minusLive()
            Winner.SECOND -> _gameData.value!!.firstPlayerData.minusLive()
            Winner.TIE -> {
                _gameData.value!!.secondPlayerData.minusLive()
                _gameData.value!!.firstPlayerData.minusLive()
            }
        }
        when (roundCounter) {
            1 -> {
                roundsData.firstRoundFirstPlayerPoints = _gameData.value!!.firstPlayerData.totalPoints
                roundsData.firstRoundSecondPlayerPoints = _gameData.value!!.secondPlayerData.totalPoints
            }
            2 -> {
                roundsData.secondRoundFirstPlayerPoints =_gameData.value!!.firstPlayerData.totalPoints
                roundsData.secondRoundSecondPlayerPoints = _gameData.value!!.secondPlayerData.totalPoints
            }
            3 -> {
                roundsData.thirdRoundFirstPlayerPoints =_gameData.value!!.firstPlayerData.totalPoints
                roundsData.thirdRoundSecondPlayerPoints =_gameData.value!!.secondPlayerData.totalPoints
            }
        }
        if (_gameData.value!!.firstPlayerData.lives == 0 || _gameData.value!!.secondPlayerData.lives == 0) {
            if (_gameData.value!!.firstPlayerData.lives == 0 && _gameData.value!!.secondPlayerData.lives == 0) {

                _gameOver.value = Winner.TIE
            } else {
                if (gameData.value!!.firstPlayerData.lives == 0) _gameOver.value = Winner.SECOND
                if (gameData.value!!.secondPlayerData.lives == 0) _gameOver.value = Winner.FIRST
            }
            _gameData.notifyDataChanged()
        } else {
            _gameData.value?.firstPlayerData?.cardsRows?.forEach { _, value ->
                value.cards = emptyList()
            }
            _gameData.value?.secondPlayerData?.cardsRows?.forEach { _, value ->
                value.cards = emptyList()
            }
            _gameData.notifyDataChanged()
        }
    }

    @MainThread
    fun onGameEnds() {

        val formatter = SimpleDateFormat(
            "hh:mm dd MMM yyyy", Locale.getDefault()
        )
        val gameScore = _gameData.value?.let {
            GameScore(
                date = formatter.format(Calendar.getInstance().time),
                firstPlayer = it.firstPlayerData.name,
                secondPlayer = it.secondPlayerData.name,
                winner = _gameOver.value!!.name,
                firstRoundFirstPlayerPoints = roundsData.firstRoundFirstPlayerPoints,
                secondRoundFirstPlayerPoints = roundsData.secondRoundFirstPlayerPoints,
                thirdRoundFirstPlayerPoints = roundsData.thirdRoundFirstPlayerPoints,
                firstRoundSecondPlayerPoints = roundsData.firstRoundSecondPlayerPoints,
                secondRoundSecondPlayerPoints = roundsData.secondRoundSecondPlayerPoints,
                thirdRoundSecondPlayerPoints = roundsData.thirdRoundSecondPlayerPoints,
                roundsCount = roundCounter
            )
        }
        GlobalScope.launch {
            gameScore?.let { gwentRepository.addGame(it) }
        }
        startReviewRequest()
    }

    override fun onCardEdit(row: CardsRow, card: Card) {
        row.apply {
            cards = cards.filterNot { it.cardId == card.cardId } + card
        }
        _gameData.notifyDataChanged()
    }

    @MainThread
    fun onGameEndsWithoutSaving() {
        startReviewRequest()
    }

    private fun startReviewRequest() {
        val manager = ReviewManagerFactory.create(getApplication<Application>().applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { r ->
            if (r.isSuccessful) {
                val reviewInfo = r.result
                _viewAction.value =
                    ViewAction.Custom(CustomViewAction.LAUNCH_REVIEW_AND_FINISH_GAME)
                        .putArg(REVIEW_MANAGER, manager)
                        .putArg(REVIEW_INFO, reviewInfo)
            } else {
                _viewAction.value = ViewAction.Custom(CustomViewAction.FINISH_GAME)
            }
        }
    }

}
