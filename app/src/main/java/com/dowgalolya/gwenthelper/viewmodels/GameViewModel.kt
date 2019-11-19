package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter
import com.dowgalolya.gwenthelper.dialogs.CardConfigDialog
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow
import com.dowgalolya.gwenthelper.entities.GameData
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.enums.CardsRowType
import com.dowgalolya.gwenthelper.enums.Player
import com.dowgalolya.gwenthelper.livedata.SingleLiveEvent
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.widgets.WeatherView

class GameViewModel(application: Application) : BaseViewModel(application),
    CardConfigDialog.OnCardCreateListener,
    WeatherView.OnWeatherChangeListener, CardRowAdapter.OnCardLongClickCallback {

    companion object {
        const val CARD_ROW = "CARD_ROW"
        const val CARD = "CARD"
    }

    object CustomViewAction {
        const val SHOW_ADD_CARD_DIALOG = "SHOW_ADD_CARD_DIALOG"
        const val SHOW_CONFIG_CARD_DIALOG = "SHOW_CONFIG_CARD_DIALOG"
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

    val rowAdapters: Map<CardsRowType, CardRowAdapter> = _selectedPlayerData.value!!.cardsRows
        .map { it.key to CardRowAdapter(it.value, this) }
        .toMap()

    private val _clickLiveEvent: SingleLiveEvent<Int> =
        SingleLiveEvent()
    val clickLiveEvent: LiveData<Int> = _clickLiveEvent

    override fun onCardSet(cardsRowType: CardsRowType, card: Card) {

        _selectedPlayerData.value!!.let { playerData ->
            playerData.cardsRows.getValue(cardsRowType).apply {
                cards += card
            }
        }
        _gameData.value = _gameData.value
    }

    @MainThread
    fun onPlusClicked(row: CardsRowType) {
        _viewAction.value =
            ViewAction.Custom(CustomViewAction.SHOW_ADD_CARD_DIALOG).putArg(CARD_ROW, row)
    }

    @MainThread
    fun onHornChecked(cardsRowType: CardsRowType, isChecked: Boolean) {
        _selectedPlayerData.value!!.let { playerData ->
            playerData.cardsRows.getValue(cardsRowType).horn = isChecked
        }
        _gameData.value = _gameData.value

    }

    override fun onWeatherChange(cardsRowType: CardsRowType, weather: Boolean) {
        _gameData.value!!.let {
            it.firstPlayerData.cardsRows.getValue(cardsRowType).badWeather = weather
            it.secondPlayerData.cardsRows.getValue(cardsRowType).badWeather = weather
        }
        _gameData.value = _gameData.value
    }

    override fun onItemLongClicked(row: CardsRow, card: Card) {
        _viewAction.value = ViewAction.Custom(CustomViewAction.SHOW_CONFIG_CARD_DIALOG)
            .putArg(CARD_ROW, row)
            .putArg(CARD, card)
    }

    @MainThread
    fun onEditClicked(row: CardsRow, card: Card) {
    }

    @MainThread
    fun onDeleteClicked(row: CardsRow, card: Card) {
        row.apply {
            cards -= card
        }
        _gameData.value = _gameData.value
    }
}
