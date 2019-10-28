package com.dowgalolya.gwenthelper.entities

import androidx.annotation.IntRange

class PlayerData {

    @IntRange(from = 0, to = 2)
    var lives: Int = 2

    val cardRows = Array(3) { CardsRow() }

    val totalPoints get() = cardRows.sumBy { it.totalPoints() }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerData

        if (!cardRows.contentEquals(other.cardRows)) return false

        return true
    }

    override fun hashCode(): Int {
        return cardRows.contentHashCode()
    }
}