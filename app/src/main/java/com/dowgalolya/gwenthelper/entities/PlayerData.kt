package com.dowgalolya.gwenthelper.entities

import androidx.annotation.IntRange
import com.dowgalolya.gwenthelper.enums.CardsRowType
import java.util.*

class PlayerData {

    @IntRange(from = 0, to = 2)
    var lives: Int = 2

    val cardsRows: Map<CardsRowType, CardsRow> =
        CardsRowType.values().associateBy({ it }, { CardsRow(it) })

    val totalPoints get() = cardsRows.values.sumBy { it.totalPoints }

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is PlayerData -> false
        lives != other.lives -> false
        cardsRows != other.cardsRows -> false
        else -> true
    }

    override fun hashCode(): Int = Objects.hash(lives, cardsRows)
}