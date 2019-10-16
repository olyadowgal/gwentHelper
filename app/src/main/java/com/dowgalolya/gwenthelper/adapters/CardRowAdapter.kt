package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R

class CardRowAdapter : RecyclerView.Adapter<CardRowAdapter.CardViewAdapter>() {

    private val cardList: MutableList<Int> = ArrayList()
    private val cardSum: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewAdapter {
        return CardViewAdapter(
            LayoutInflater.from(parent.context).inflate(
                R.layout.blue_card_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewAdapter, position: Int) {
        holder.onBind(cardList[position])
    }

    fun add(card : Int) {
        cardList.add(card)
        notifyItemInserted(cardList.size)
    }

    inner class CardViewAdapter(view: View) : RecyclerView.ViewHolder(view) {

        private val cardValue: TextView = view.findViewById(R.id.card_value)

        fun onBind(item: Int) {
            cardValue.text = item.toString()
        }
    }
}