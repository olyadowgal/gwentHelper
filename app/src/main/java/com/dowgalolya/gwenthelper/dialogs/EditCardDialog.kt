package com.dowgalolya.gwenthelper.dialogs

import android.content.Context
import android.content.DialogInterface
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow
import com.dowgalolya.gwenthelper.enums.CardsRowType

class EditCardDialog(
    context: Context,
    private val listener: OnCardEditListener,
    private val cardsRow: CardsRow,
    private val card: Card
) : BaseCardDialog(context), DialogInterface.OnClickListener {

    interface OnCardEditListener {

        fun onCardEdit(row: CardsRow, card: Card)
    }

    init {
        setButton(DialogInterface.BUTTON_POSITIVE, "EDIT CARD", this)
        setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", this)
//        txt_card_points.text = card.points.toString()
//        card.abilities
//            .map {
//                when (it) {
//                    Ability.HERO -> sw_hero.isChecked = true
//                    Ability.DECOY -> sw_decoy.isChecked = true
//                    Ability.HORN -> sw_horn.isChecked = true
//                    Ability.MORALE_BOOST -> sw_moral_boost.isChecked = true
//                    Ability.TIGHT_BOND -> sw_tight_bond.isChecked = true
//                    else -> throw RuntimeException()
//                }
//            }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                listener.onCardEdit(
                    cardsRow,
                    generateCard(card.cardId, textCardPoints.text.toString().toInt())
                )
                dismiss()
            }
            DialogInterface.BUTTON_NEGATIVE -> cancel()
        }
    }

}