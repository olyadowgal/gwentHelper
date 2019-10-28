package com.dowgalolya.gwenthelper.viewmodels

import androidx.lifecycle.*
import com.dowgalolya.gwenthelper.CardConfigDialog
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.GameData
import com.dowgalolya.gwenthelper.entities.PlayerData

class GameViewModel : ViewModel(), CardConfigDialog.OnCardCreateListener {

    enum class Player {
        FIRST,
        SECOND
    }

    private val _gameData = MutableLiveData<GameData>().apply {
        value = GameData(
            firstPlayerData = PlayerData(),
            secondPlayerData = PlayerData()
        )
    }
    val gameData: LiveData<GameData> = _gameData

    private val _selectedPlayer = MutableLiveData<Player>().apply {
        value = Player.FIRST//TODO: check it
    }
    val selectedPlayer: LiveData<Player> = _selectedPlayer

    private val _selectedPlayerData = MediatorLiveData<PlayerData>().apply {
        value = _gameData.value!!.firstPlayerData//TODO: check it
        val observer: Observer<Any?> = Observer {
            value = when (selectedPlayer.value!!) {
                Player.FIRST -> _gameData.value!!.firstPlayerData
                Player.SECOND -> _gameData.value!!.secondPlayerData
            }
            rowAdapters.forEachIndexed { index, adapter -> adapter.row = value!!.cardRows[index] }
        }
        addSource(_gameData, observer)
        addSource(_selectedPlayer, observer)
    }
    val selectedPlayerData: LiveData<PlayerData> = _selectedPlayerData

    val rowAdapters: Array<CardRowAdapter> = Array(3) {
        CardRowAdapter(_selectedPlayerData.value!!.cardRows[it])
    }

    override fun onCardSet(buttonId: Int, card: Card) {
        val rowId = when (buttonId) {
            R.id.cv_user1_close_combat -> 0
            R.id.cv_user1_long_range -> 1
            R.id.cv_user1_siege -> 2
            else -> throw IllegalArgumentException()
        }

        _selectedPlayerData.value!!.let { playerData ->
            playerData.cardRows[rowId] = playerData.cardRows[rowId].let {
                it.copy(
                    cards = it.cards + card
                )
            }
        }
        _gameData.value = _gameData.value
    }
}
