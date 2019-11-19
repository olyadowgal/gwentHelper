package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.entities.Ability
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.blue_card_item.view.*

class CardRowAdapter(cardsRow: CardsRow, private val callback: OnCardLongClickCallback) :
    RecyclerView.Adapter<CardRowAdapter.CardViewHolder>() {

    init {
        setHasStableIds(true)
    }

    interface OnCardLongClickCallback {
        fun onItemLongClicked(row : CardsRow, card : Card)
    }

    var row: CardsRow = cardsRow
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
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

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(row.cards[position])
    }

    override fun getItemId(position: Int): Long {
        return row.cards[position].hashCode().toLong()
    }

    inner class CardViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer, View.OnLongClickListener {

        private val context get() = containerView.context

        init {
            containerView.card_view.setOnLongClickListener(this)
        }

        fun onBind(item: Card) {
            containerView.txt_card_value.text = row.pointsOf(item).toString()
            if (item.abilities.contains(Ability.HERO)) {
                containerView.card_view.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorCardHero
                    )
                )
            } else {
                containerView.card_view.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorCardBlue
                    )
                )
            }
            val abilityIconRes =
                item.abilities.filterNot { it == Ability.HERO }.firstOrNull()?.iconRes
            if (abilityIconRes == null) {
                containerView.iv_card_type.visibility = View.INVISIBLE
            } else {
                containerView.iv_card_type.visibility = View.VISIBLE
                containerView.iv_card_type.setImageResource(abilityIconRes)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            callback.onItemLongClicked(row, row.cards[adapterPosition])
            return true
        }
    }
}