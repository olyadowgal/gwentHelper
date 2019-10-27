package com.dowgalolya.gwenthelper.entities

import kotlin.math.min
import kotlin.math.pow

data class CardsRow(val cards: List<Card>, val horn: Boolean) {

    fun totalPoints(badWeather: Boolean) = cards.sumBy { pointsOf(it, badWeather) }

    val hornsCount = (if (horn) 1 else 0) + cards.count { it.abilities.contains(Ability.HORN) }

    fun pointsOf(card: Card, badWeather: Boolean): Int {
        if (card.abilities.contains(Ability.DECOY)) return 0

        var points = card.points

        //WEATHER
        if (badWeather && !card.abilities.contains(Ability.HERO)) {
            points = min(points, 1)
        }

        //TIGHT_BOND
        if (card.abilities.contains(Ability.TIGHT_BOND)) {
            points = points.toDouble()
                .pow(cards.count { it.points == card.points && it.abilities.contains(Ability.TIGHT_BOND) })
                .toInt()
        }


        //MARDROEME
        //TODO

        //HORN
        val hornsCount =
            if (card.abilities.contains(Ability.HORN)) this.hornsCount - 1 else this.hornsCount
        if (hornsCount > 0) {
            repeat(hornsCount) {
                points *= 2
            }
        }

        //MORALE_BOOST
        points += cards.count { it.abilities.contains(Ability.MORALE_BOOST) }
        if (card.abilities.contains(Ability.MORALE_BOOST)) {
            points--
        }

        return points
    }
}