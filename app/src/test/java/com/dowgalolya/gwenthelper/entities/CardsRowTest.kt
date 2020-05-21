package com.dowgalolya.gwenthelper.entities

import com.dowgalolya.gwenthelper.enums.CardsRowType
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class CardsRowTest {

    //region pointsOf tests

    @Test
    fun `When pointsOf was called with a card that not belong to this row, then should throw IllegalArgumentException`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.HORN
            )
        )
        val card2 = Card(
            points = 3,
            abilities = listOf(
                Ability.HORN
            )
        )
        val card3 = Card(
            points = 10,
            abilities = listOf(
                Ability.HERO
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2
            ),
            horn = false,
            badWeather = false
        )

        //When, then
        try {
            row.pointsOf(card3)
            fail()
        } catch (ignored: IllegalArgumentException) { }
    }

    //region Regular Card Tests

    @Test
    fun `Given row has one regular card without abilities with 5 points, when pointsOf was called for this card, then should return 5`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = emptyList()
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(5, result)
    }

    @Test
    fun `Given row with two cards - regular card and morale boost card , when pointsOf was called for regular card, then should return points + 1`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = emptyList()
        )
        val card2 = Card(
            points = 5,
            abilities = listOf(
                Ability.MORALE_BOOST
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(6, result)
    }

    @Test
    fun `Given row with a horn has one regular card without abilities, when pointsOf was called for this card, then should multiply by 2`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = emptyList()
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = true,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(10, result)
    }

    @Test
    fun `Given row without horn has two cards ( with horn , without abilities), when pointsOf was called for card without abilities, then should multiply by 2`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.HORN
            )
        )

        val card2 = Card(
            points = 5,
            abilities = emptyList()
        )


        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card2)

        //Then
        assertEquals(10, result)
    }

    @Test
    fun `Given row with bad weather and one card without abilities, when pointsOf was called for this card, then should return 1`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = emptyList()
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = false,
            badWeather = true
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(1, result)
    }

    @Test
    fun `Given row with bad weather, horn and regular card, when pointsOf was called for this card, then should return 2`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = emptyList()
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = true,
            badWeather = true
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(2, result)
    }

    //endregion

    //region Hero Card Tests

    @Test
    fun `Given row with bad weather and card with hero ability, when pointsOf was called for this card, then points should remain still`() {
        //Given
        val card1 = Card(
            points = 10,
            abilities = listOf(
                Ability.HERO
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = false,
            badWeather = true
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(10, result)
    }

    @Test
    fun `Given row with horn and card with hero ability, when pointsOf was called for this card, then points should remain still`() {
        //Given
        val card1 = Card(
            points = 10,
            abilities = listOf(
                Ability.HERO
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = true,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(10, result)
    }

    @Test
    fun `Given row with two cards - card with hero ability and card with horn, when pointsOf was called for card with hero ability, then points should remain still`() {
        //Given
        val card1 = Card(
            points = 10,
            abilities = listOf(
                Ability.HERO
            )
        )

        val card2 = Card(
            points = 10,
            abilities = listOf(
                Ability.HORN
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(10, result)
    }

    //endregion

    //region Tight Bond Tests

    @Test
    fun `Given row has one card with tight bond ability with 5 points, when pointsOf was called for this card, then should return 5`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(5, result)
    }

    @Test
    fun `Given row has two cards with tight bond but different points, when pointsOf was called for any of this card, then should return points without change`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card2 = Card(
            points = 6,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(5, result)
    }

    @Test
    fun `Given row has two cards with tight bond with equals points, when pointsOf was called for any of this card, then should return points in square`() {
        //Given
        val card1 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card2 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(4, result)
    }

    @Test
    fun `Given row has three cards with tight bond with equals points, when pointsOf was called for any of this card, then should return points in cube`() {
        //Given
        val card1 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card2 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card3 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2,
                card3
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(8, result)
    }

    @Test
    fun `Given row with bad weather has three cards with tight bond with equals points, when pointsOf was called for any of this card, then should return 1`() {
        //Given
        val card1 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card2 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card3 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2,
                card3
            ),
            horn = false,
            badWeather = true
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(1, result)
    }

    @Test
    fun `Given row with bad weather has four cards - three tight bond with equals points and one horn, when pointsOf was called for tight bond card, then should return points in cube multiplied by two`() {
        //Given
        val card1 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card2 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card3 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card4 = Card(
            points = 2,
            abilities = listOf(
                Ability.HORN
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2,
                card3,
                card4
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(16, result)
    }

    @Test
    fun `Given row has four cards - three with tight bond and equal points and one morale boost, when pointsOf was called for any tight bond card, then should return points in cube + 1`() {
        //Given
        val card1 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card2 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card3 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card4 = Card(
            points = 1,
            abilities = listOf(
                Ability.MORALE_BOOST
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2,
                card3,
                card4
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(9, result)
    }

    @Test
    fun `Given row with horn has three cards with tight bond with equals points, when pointsOf was called for any of this card, then should return points in cube multiplied by 2`() {
        //Given
        val card1 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card2 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val card3 = Card(
            points = 2,
            abilities = listOf(
                Ability.TIGHT_BOND
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1,
                card2,
                card3
            ),
            horn = true,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(16, result)
    }

    //endregion

    //region Decoy Card Tests

    @Test
    fun `Given row has one decoy card with 5 points, when pointsOf was called for this card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.DECOY
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(0, result)
    }

    @Test
    fun `Given row with horn has one decoy card, when pointsOf was called for this card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.DECOY
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = true,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(0, result)
    }

    @Test
    fun `Given row with bad weather has one decoy card, when pointsOf was called for this card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.DECOY
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = false,
            badWeather = true
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(0, result)
    }

    @Test
    fun `Given row with horn has horn card and decoy card, when pointsOf was called for decoy card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.DECOY
            )
        )

        val card2 = Card(
            points = 5,
            abilities = listOf(
                Ability.HORN
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(0, result)
    }

    @Test
    fun `Given row with horn has morale boost card and decoy card, when pointsOf was called for decoy card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = listOf(
                Ability.DECOY
            )
        )

        val card2 = Card(
            points = 5,
            abilities = listOf(
                Ability.MORALE_BOOST
            )
        )

        val row = CardsRow(
            type = CardsRowType.SIEGE,
            cards = listOf(
                card1
            ),
            horn = false,
            badWeather = false
        )

        //When
        val result = row.pointsOf(card1)

        //Then
        assertEquals(0, result)
    }

    //endregion

    //endregion
}