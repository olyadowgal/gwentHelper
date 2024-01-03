package com.dowgalolya.gwenthelper.dialogs

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.databinding.CardDialogFragmentBinding
import com.dowgalolya.gwenthelper.databinding.ViewUserBinding
import com.dowgalolya.gwenthelper.enums.Ability
import com.dowgalolya.gwenthelper.entities.Card
import java.util.*

abstract class BaseCardDialog(context: Context) : AlertDialog(context), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    val textCardPoints: TextView
    var binding: CardDialogFragmentBinding? = null;

    init {
        binding = CardDialogFragmentBinding.inflate(LayoutInflater.from(context))
        setView(binding!!.root)

        textCardPoints = binding!!.txtCardPoints
        val view = binding!!.root
        view.findViewById<Button>(R.id.btn_minus_value).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_plus_value).setOnClickListener(this)
        view.findViewById<Switch>(R.id.sw_decoy).setOnCheckedChangeListener(this)
        view.findViewById<Switch>(R.id.sw_hero).setOnCheckedChangeListener(this)
    }

    fun generateCard(value: Int): Card {
        val abilities =
            arrayOf(
                binding!!.swHero,
                binding!!.swDecoy,
                binding!!.abilityHorn,
                binding!!.abilityMoralBoost,
                binding!!.abilityTightBond
            )
                .filter { it.isChecked }
                .map {
                    when (it.id) {
                        R.id.sw_hero -> Ability.HERO
                        R.id.sw_decoy -> Ability.DECOY
                        R.id.ability_horn -> Ability.HORN
                        R.id.ability_moral_boost -> Ability.MORALE_BOOST
                        R.id.ability_tight_bond -> Ability.TIGHT_BOND
                        else -> throw RuntimeException()
                    }
                }

        return Card(points = value, abilities = abilities)
    }

    fun generateCard(id: UUID, value: Int): Card {
        val abilities =
            arrayOf(
                binding!!.swHero,
                binding!!.swDecoy,
                binding!!.abilityHorn,
                binding!!.abilityMoralBoost,
                binding!!.abilityTightBond
            )
                .filter { it.isChecked }
                .map {
                    when (it.id) {
                        R.id.sw_hero -> Ability.HERO
                        R.id.sw_decoy -> Ability.DECOY
                        R.id.ability_horn -> Ability.HORN
                        R.id.ability_moral_boost -> Ability.MORALE_BOOST
                        R.id.ability_tight_bond -> Ability.TIGHT_BOND
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
                binding!!.swHero.isEnabled = !isChecked
                binding!!.swHero.isClickable = !isChecked
                binding!!.radioCardAbilities.isEnabled = !isChecked
                binding!!.abilityHorn.isClickable = !isChecked
                binding!!.abilityMoralBoost.isClickable = !isChecked
                binding!!.abilityTightBond.isClickable = !isChecked
                binding!!.btnMinusValue.isClickable = !isChecked
                binding!!.btnPlusValue.isClickable = !isChecked
                binding!!.radioCardAbilities.clearCheck()
                if (isChecked) binding!!.txtCardPoints.text = "0"
            }

            R.id.sw_hero -> {
                binding!!.swDecoy.isClickable = !isChecked
                binding!!.swDecoy.isEnabled = !isChecked
            }
        }
    }

}