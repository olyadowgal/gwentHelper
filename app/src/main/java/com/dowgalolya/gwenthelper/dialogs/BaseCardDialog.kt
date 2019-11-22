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
import kotlinx.android.synthetic.main.card_dialog_fragment.*
import java.util.*

abstract class BaseCardDialog(context: Context) : AlertDialog(context), View.OnClickListener {

   val textCardPoints: TextView

    init {
        val view = View.inflate(
            context,
            R.layout.card_dialog_fragment, null
        )
        setView(view)

        textCardPoints = view.findViewById(R.id.txt_card_points)
        view.findViewById<Button>(R.id.btn_minus_value).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_plus_value).setOnClickListener(this)
    }

    fun generateCard(value: Int): Card {
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

        return Card( points = value, abilities = abilities)
    }

    fun generateCard(id : UUID, value: Int) : Card {
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

        return Card( cardId = id, points = value, abilities = abilities)
    }

    override fun onClick(view : View) {
        when (view.id) {
            R.id.btn_plus_value -> {
                Log.d(AddCardDialog.TAG, textCardPoints.text.toString())
                textCardPoints.text = (textCardPoints.text.toString().toInt() + 1).toString()
            }
            R.id.btn_minus_value -> {
                textCardPoints.text = (textCardPoints.text.toString().toInt() - 1).toString()
            }
        }
    }
}