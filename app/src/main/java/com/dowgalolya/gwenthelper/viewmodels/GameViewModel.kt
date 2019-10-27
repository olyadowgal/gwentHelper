package com.dowgalolya.gwenthelper.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dowgalolya.gwenthelper.CardConfigDialog
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter
import kotlinx.android.synthetic.main.game_fragment.view.*

class GameViewModel : ViewModel(), CardConfigDialog.OnCardCreateListener {

    val user1CloseCombatRowAdapter: CardRowAdapter = CardRowAdapter()
    val user1LongRangeAdapter: CardRowAdapter = CardRowAdapter()
    val user1SiegeAdapter : CardRowAdapter = CardRowAdapter()

    private val _user1CloseCombatRowPoints = MutableLiveData<Int>()
    val user1CloseCombatRowPoints: LiveData<Int> = _user1CloseCombatRowPoints

    private val _user1LongRangePoints = MutableLiveData<Int>()
    val user1LongRangePoints: LiveData<Int> = _user1LongRangePoints

    private val _user1SiegePoints = MutableLiveData<Int>()
    val user1SiegePoints: LiveData<Int> = _user1SiegePoints


    override fun onCardSet(buttonId: Int, cardValue: Int) {

        when (buttonId) {
            R.id.btn_add_user1_close_combat -> {
                if (_user1CloseCombatRowPoints.value == null) {
                    _user1CloseCombatRowPoints.value = cardValue
                } else {
                    _user1CloseCombatRowPoints.value = _user1CloseCombatRowPoints.value?.plus(cardValue)
                }
                user1CloseCombatRowAdapter.add(cardValue)}

            R.id.btn_add_user1_long_range -> {
                if (_user1LongRangePoints.value == null) {
                    _user1LongRangePoints.value = cardValue
                } else {
                    _user1LongRangePoints.value = _user1LongRangePoints.value?.plus(cardValue)
                }
                user1LongRangeAdapter.add(cardValue)
            }

            R.id.btn_add_user1_siege -> {
                if (_user1SiegePoints.value == null) {
                    _user1SiegePoints.value = cardValue
                } else {
                    _user1SiegePoints.value = _user1SiegePoints.value?.plus(cardValue)
                }
                user1SiegeAdapter.add(cardValue)
            }
        }
    }
}
