package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow

class CardRowAdapter : RecyclerView.Adapter<CardRowAdapter.CardViewAdapter>() {

    private var row: CardsRow = CardsRow(emptyList(), false)
    private var badWeather = false

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
        return row.cards.size
    }

    override fun onBindViewHolder(holder: CardViewAdapter, position: Int) {
        holder.onBind(row.cards[position])
    }

    fun add(card : Card) {
        //TODO HUINYA
        row = row.copy(
            cards = row.cards + card
        )
        notifyItemInserted(row.cards.size)
    }

    inner class CardViewAdapter(view: View) : RecyclerView.ViewHolder(view) {

        private val cardValue: TextView = view.findViewById(R.id.card_value)

        fun onBind(item: Card) {
            cardValue.text = row.pointsOf(item, badWeather).toString()
        }
    }
}