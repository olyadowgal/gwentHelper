package com.dowgalolya.gwenthelper.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dowgalolya.gwenthelper.CardConfigDialog
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow

class GameViewModel : ViewModel(), CardConfigDialog.OnCardCreateListener {

    val user1CloseCombatRowAdapter: CardRowAdapter = CardRowAdapter()
    val user1LongRangeAdapter: CardRowAdapter = CardRowAdapter()
    val user1SiegeAdapter: CardRowAdapter = CardRowAdapter()

    private val _user1CloseCombatRowPoints = MutableLiveData<CardsRow>()
    val user1CloseCombatRowPoints: LiveData<CardsRow> = _user1CloseCombatRowPoints

    private val _user1LongRangePoints = MutableLiveData<CardsRow>()
    val user1LongRangePoints: LiveData<CardsRow> = _user1LongRangePoints

    private val _user1SiegePoints = MutableLiveData<CardsRow>()
    val user1SiegePoints: LiveData<CardsRow> = _user1SiegePoints


    override fun onCardSet(buttonId: Int, card: Card) {

        when (buttonId) {
            R.id.cv_user1_close_combat -> {
                user1CloseCombatRowAdapter.add(card)
                _user1CloseCombatRowPoints.value = user1CloseCombatRowAdapter.getRow()
            }

            R.id.cv_user1_long_range -> {
                user1LongRangeAdapter.add(card)
                _user1LongRangePoints.value = user1LongRangeAdapter.getRow()
            }

            R.id.cv_user1_siege -> {
                user1SiegeAdapter.add(card)
                _user1SiegePoints.value = user1SiegeAdapter.getRow()
            }
        }
    }
}
