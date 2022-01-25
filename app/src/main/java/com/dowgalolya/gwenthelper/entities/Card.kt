package com.dowgalolya.gwenthelper.entities

import com.dowgalolya.gwenthelper.enums.Ability
import java.util.*

data class Card (
    val cardId : UUID = UUID.randomUUID(),
    val points : Int,
    val abilities: List<Ability>
)