package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.dowgalolya.gwenthelper.databinding.ViewCardsStatsBinding


class  CardsStatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var binding: ViewCardsStatsBinding? = null

    init {
        binding = ViewCardsStatsBinding.inflate(LayoutInflater.from(context),this)
    }

    fun setCardCounterValue(value: Int) {
        binding!!.txtCardsCounter.text = value.toString()
    }

    fun setHornValue(isChecked : Boolean) {
        binding!!.cbHorn.isChecked = isChecked
    }
}