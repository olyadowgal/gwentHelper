package com.dowgalolya.gwenthelper.dialogs

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.*
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.entities.Ability
import com.dowgalolya.gwenthelper.entities.Card
import kotlinx.android.synthetic.main.card_dialog_fragment.*
import java.util.*

abstract class BaseCardDialog(context: Context) : AlertDialog(context), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

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
        view.findViewById<Switch>(R.id.sw_decoy).setOnCheckedChangeListener(this)
        view.findViewById<Switch>(R.id.sw_hero).setOnCheckedChangeListener(this)
    }

    fun generateCard(value: Int): Card {
        val abilities =
            arrayOf(sw_hero, sw_decoy, ability_horn, ability_moral_boost, ability_tight_bond)
                .filter { it.isChecked }
                .map {
                    when (it) {
                        sw_hero -> Ability.HERO
                        sw_decoy -> Ability.DECOY
                        ability_horn -> Ability.HORN
                        ability_moral_boost -> Ability.MORALE_BOOST
                        ability_tight_bond -> Ability.TIGHT_BOND
                        else -> throw RuntimeException()
                    }
                }

        return Card(points = value, abilities = abilities)
    }

    fun generateCard(id: UUID, value: Int): Card {
        val abilities =
            arrayOf(sw_hero, sw_decoy, ability_horn, ability_moral_boost, ability_tight_bond)
                .filter { it.isChecked }
                .map {
                    when (it) {
                        sw_hero -> Ability.HERO
                        sw_decoy -> Ability.DECOY
                        ability_horn -> Ability.HORN
                        ability_moral_boost -> Ability.MORALE_BOOST
                        ability_tight_bond -> Ability.TIGHT_BOND
                        else -> throw RuntimeException()
                    }
                }

        return Card(cardId = id, points = value, abilities = abilities)
    }

    override fun onClick(view: View) {
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

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.sw_decoy -> {
                sw_hero.isEnabled = !isChecked
                sw_hero.isClickable = !isChecked
                radio_card_abilities.isEnabled = !isChecked
                ability_horn.isClickable = !isChecked
                ability_moral_boost.isClickable = !isChecked
                ability_tight_bond.isClickable = !isChecked
                btn_minus_value.isClickable = !isChecked
                btn_plus_value.isClickable = !isChecked
                radio_card_abilities.clearCheck()
                if (isChecked) txt_card_points.text = "0"
            }
            R.id.sw_hero -> {
                sw_decoy.isClickable = !isChecked
                sw_decoy.isEnabled = !isChecked
            }
        }
    }

}