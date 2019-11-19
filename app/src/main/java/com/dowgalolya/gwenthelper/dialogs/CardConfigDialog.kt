package com.dowgalolya.gwenthelper.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.entities.Ability
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.enums.CardsRowType
import kotlinx.android.synthetic.main.card_dialog_fragment.*

class CardConfigDialog(
    context: Context?,
    private val listener: OnCardCreateListener,
    private val cardsRowType: CardsRowType
) : AlertDialog(context), DialogInterface.OnClickListener, View.OnClickListener {

    companion object {
        const val TAG = "CARDDIALOG"
    }

    interface OnCardCreateListener {

        fun onCardSet(cardsRowType: CardsRowType, card: Card)
    }

    private val textCardPoints: TextView

    init {
        val view = View.inflate(
            context,
            R.layout.card_dialog_fragment, null
        )
        setView(view)

        setButton(DialogInterface.BUTTON_POSITIVE, "ADD CARD", this)
        setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", this)

        textCardPoints = view.findViewById(R.id.txt_card_points)
        view.findViewById<Button>(R.id.btn_minus_value).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_plus_value).setOnClickListener(this)
    }

    private fun generateCard(value: Int): Card {
        val abilities = arrayOf(sw_hero, sw_decoy, sw_horn, sw_moral_boost, sw_tight_bond)
            .filter { it.isChecked }
            .map {
                when (it) {
                    sw_hero -> Ability.HERO
                    sw_decoy -> Ability.DECOY
                    sw_horn -> Ability.HORN
                    sw_moral_boost -> Ability.MORALE_BOOST
                    sw_tight_bond -> Ability.TIGHT_BOND
                    else -> throw RuntimeException()
                }
            }

        return Card(0, value, abilities)
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                Log.d(TAG, textCardPoints.text.toString())

                listener.onCardSet(
                    cardsRowType,
                    generateCard(textCardPoints.text.toString().toInt())
                )
                dismiss()
            }
            DialogInterface.BUTTON_NEGATIVE -> cancel()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_plus_value -> {
                Log.d(TAG, textCardPoints.text.toString())
                textCardPoints.text = (textCardPoints.text.toString().toInt() + 1).toString()
            }
            R.id.btn_minus_value -> {
                textCardPoints.text = (textCardPoints.text.toString().toInt() - 1).toString()
            }
        }
    }

}