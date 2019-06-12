package com.dowgalolya.gwenthelper.viewmodels

import androidx.lifecycle.ViewModel
import com.dowgalolya.gwenthelper.CardConfigDialog
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter

class GameViewModel : ViewModel(), CardConfigDialog.OnCardCreateListener {

    val cardRowAdapter : CardRowAdapter = CardRowAdapter()

    override fun onCardSet(cardValue: Int) {
        cardRowAdapter.add(cardValue)
    }
}
