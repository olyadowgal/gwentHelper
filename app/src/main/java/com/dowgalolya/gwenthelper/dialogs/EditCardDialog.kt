package com.dowgalolya.gwenthelper.dialogs

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.enums.Ability
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow

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
        setButton(DialogInterface.BUTTON_POSITIVE, getContext().getString(R.string.edit_card), this)
        setButton(DialogInterface.BUTTON_NEGATIVE, getContext().getString(R.string.cancel), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding!!.txtCardPoints.text = card.points.toString()
        card.abilities
            .map {
                when (it) {
                    Ability.HERO -> binding!!.swHero.isChecked = true
                    Ability.DECOY -> binding!!.swDecoy.isChecked = true
                    Ability.HORN -> binding!!.abilityHorn.isChecked = true
                    Ability.MORALE_BOOST -> binding!!.abilityMoralBoost.isChecked = true
                    Ability.TIGHT_BOND -> binding!!.abilityTightBond.isChecked = true
                    else -> throw RuntimeException()
                }
            }

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