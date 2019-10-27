package com.dowgalolya.gwenthelper.entities

import androidx.annotation.StringRes
import com.dowgalolya.gwenthelper.R

enum class Ability(@StringRes val nameRes: Int) {

    HERO(R.string.ability_hero),
    MORALE_BOOST(R.string.ability_morale_boost),
    DECOY(R.string.ability_decoy),
    HORN(R.string.ability_horn),
    MARDROEME(R.string.ability_mardroeme),
    YOUNG_BERSERKER(R.string.ability_young_berserker),
    BERSERKER(R.string.ability_berserker),
    TIGHT_BOND(R.string.ability_tight_bond)
}