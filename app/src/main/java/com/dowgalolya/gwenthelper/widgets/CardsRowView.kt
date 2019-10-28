package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter
import kotlinx.android.synthetic.main.view_cards_row.view.*

class CardsRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_cards_row, this)
    }

    fun setCardRowAdapter(adapter: CardRowAdapter) {
        rv_cards_row.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv_cards_row.adapter = adapter
    }

    fun setOnButtonClickListener(clickListener: OnClickListener) {
        btn_add_card.setOnClickListener(clickListener)
    }

    fun setCardCounterValue(value: Int) {
        txt_cards_counter.text = value.toString()
    }
}