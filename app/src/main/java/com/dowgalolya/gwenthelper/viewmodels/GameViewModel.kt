package com.dowgalolya.gwenthelper.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dowgalolya.gwenthelper.CardConfigDialog
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter

class GameViewModel : ViewModel(), CardConfigDialog.OnCardCreateListener {

    val cardRowAdapter: CardRowAdapter = CardRowAdapter()

    private val _currentRowPoints = MutableLiveData<Int>()
    val currentRowPoints: LiveData<Int> = _currentRowPoints

    override fun onCardSet(cardValue: Int) {
        if (_currentRowPoints.value == null) {
            _currentRowPoints.value = cardValue
        } else {
            _currentRowPoints.value = _currentRowPoints.value?.plus(cardValue)
        }
        cardRowAdapter.add(cardValue)
    }
}
