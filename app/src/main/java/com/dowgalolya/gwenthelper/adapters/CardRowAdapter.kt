package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.entities.Card

class CardRowAdapter() : RecyclerView.Adapter<CardRowAdapter.CardViewAdapter>() {

    private val cardList: List<Card> = emptyList()

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

    inner class CardViewAdapter(view: View) : RecyclerView.ViewHolder(view) {

        private val cardValue: TextView = view.findViewById(R.id.card_value)

        fun onBind(item: Card) {
            cardValue.text = item.points.toString()
        }
    }
}