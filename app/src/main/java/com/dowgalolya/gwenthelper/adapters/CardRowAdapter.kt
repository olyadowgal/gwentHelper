package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.enums.Ability
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow
import com.dowgalolya.gwenthelper.enums.CardsRowType
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_card.view.*

class CardRowAdapter private constructor(
    cardsRow: CardsRow,
    private val callback: Callback
) : RecyclerView.Adapter<CardRowAdapter.BaseViewHolder>() {

    class Factory() {

        fun create(cardsRow: CardsRow, callback: Callback) = CardRowAdapter(cardsRow, callback)
    }

    interface Callback {

        fun onLongClick(row: CardsRow, card: Card)
        fun onClick(row: CardsRowType)
    }

    object ViewType {

        const val ADD_BUTTON = R.layout.item_plus_button
        const val CARD = R.layout.item_card
    }

    init {
        setHasStableIds(true)
    }

    var row: CardsRow = cardsRow
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            ViewType.CARD -> CardItemViewHolder(view)
            ViewType.ADD_BUTTON -> AddCardItemViewHolder(view)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = row.cards.size + 1

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is CardItemViewHolder -> holder.onBind(row.cards[position])
        }
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        row.cards.size -> ViewType.ADD_BUTTON
        else -> ViewType.CARD
    }

    override fun getItemId(position: Int): Long = row.cards.getOrNull(position).hashCode().toLong()

    //region ViewHolders

    abstract inner class BaseViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer

    inner class AddCardItemViewHolder(
        override val containerView: View
    ) : BaseViewHolder(containerView), View.OnClickListener {

        init {
            containerView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            callback.onClick(row.type)
        }
    }

    inner class CardItemViewHolder(
        override val containerView: View
    ) : BaseViewHolder(containerView), View.OnLongClickListener {

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
                        R.color.colorHeroCard
                    )
                )
            } else {
                containerView.card_view.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSimpleCard
                    )
                )
            }
            val abilityIconRes =
                item.abilities.filterNot { it == Ability.HERO }.firstOrNull()?.iconRes
            if (abilityIconRes == null) {
                containerView.img_card_type.visibility = View.INVISIBLE
            } else {
                containerView.img_card_type.visibility = View.VISIBLE
                containerView.img_card_type.setImageResource(abilityIconRes)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            callback.onLongClick(row, row.cards[adapterPosition])
            return true
        }
    }

    //endregion
}