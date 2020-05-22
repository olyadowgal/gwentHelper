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
    fun `Given row that contains one regular card, when pointsOf was called for this card, then should return origin points count of that card`() {
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
    fun `Given row that contains two cards - regular card and morale boost card , when pointsOf was called for regular card, then should return points + 1`() {
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
    fun `Given row with a horn that contains one regular card without abilities, when pointsOf was called for this card, then should return points multiplied by 2`() {
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
    fun `Given row that contains two cards (with horn , without abilities), when pointsOf was called for card without abilities, then should return points multiplied by 2`() {
        //Given
        val card1 = Card(
            points = 2,
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
    fun `Given row with bad weather that contains one regular card, when pointsOf was called for this card, then should return 1`() {
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
    fun `Given row with bad weather and horn that contains one regular card, when pointsOf was called for this card, then should return 2`() {
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
    fun `Given row with bad weather that contains a card with HERO ability, when pointsOf was called for this card, then should return its points unchanged`() {
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
    fun `Given row with horn that contains a card with HERO ability, when pointsOf was called for this card, then should return its points unchanged`() {
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
    fun `Given row that contains two cards - card with HERO ability and card with horn, when pointsOf was called for card with HERO ability, then should return its points unchanged`() {
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
    fun `Given row that contains one card with TIGHT_BOND ability, when pointsOf was called for this card, then should return its points unchanged`() {
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
    fun `Given row that contains two cards with TIGHT_BOND but different amount of points, when pointsOf was called for any of this card, then should return its points unchanged`() {
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
        val result1 = row.pointsOf(card1)
        val result2 = row.pointsOf(card2)

        //Then
        assertEquals(5, result1)
        assertEquals(6, result2)
    }

    @Test
    fun `Given row that contains two cards with TIGHT_BOND with equals points, when pointsOf was called for any of this card, then should return its points in square`() {
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
        val result1 = row.pointsOf(card1)
        val result2 = row.pointsOf(card2)

        //Then
        assertEquals(4, result1)
        assertEquals(4, result2)

    }

    @Test
    fun `Given row that contains three cards with TIGHT_BOND with equals points, when pointsOf was called for any of this card, then should return its points in cube`() {
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
        val result1 = row.pointsOf(card1)
        val result2 = row.pointsOf(card2)
        val result3 = row.pointsOf(card3)

        //Then
        assertEquals(8, result1)
        assertEquals(8,result2)
        assertEquals(8, result3)
    }

    @Test
    fun `Given row with bad weather that contains three cards with TIGHT_BOND with equals points, when pointsOf was called for any of this card, then should return 1`() {
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
        val result1 = row.pointsOf(card1)
        val result2 = row.pointsOf(card2)
        val result3 = row.pointsOf(card3)

        //Then
        assertEquals(1, result1)
        assertEquals(1, result2)
        assertEquals(1, result3)
    }

    @Test
    fun `Given row with bad weather that contains four cards - three TIGHT_BOND with equals points and one HORN, when pointsOf was called for any of TIGHT_BOND card, then should return its points in cube multiplied by two`() {
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
        val result1 = row.pointsOf(card1)
        val result2 = row.pointsOf(card2)
        val result3 = row.pointsOf(card3)

        //Then
        assertEquals(16, result1)
        assertEquals(16, result2)
        assertEquals(16, result3)
    }

    @Test
    fun `Given row that contains four cards - three with TIGHT_BOND and equal points and one MORALE_BOOST, when pointsOf was called for any TIGHT_BOND card, then should return its points in cube + 1`() {
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
        val result1 = row.pointsOf(card1)
        val result2 = row.pointsOf(card2)
        val result3 = row.pointsOf(card3)

        //Then
        assertEquals(9, result1)
        assertEquals(9, result2)
        assertEquals(9, result3)
    }

    @Test
    fun `Given row with HORN that contains three cards with TIGHT_BOND with equals points, when pointsOf was called for any of this card, then should return its points in cube multiplied by 2`() {
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
        val result1 = row.pointsOf(card1)
        val result2 = row.pointsOf(card2)
        val result3 = row.pointsOf(card3)

        //Then
        assertEquals(16, result1)
        assertEquals(16, result2)
        assertEquals(16, result3)
    }

    //endregion

    //region Decoy Card Tests

    @Test
    fun `Given row that contains one DECOY card with non-zero points, when pointsOf was called for this card, then should return 0`() {
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
    fun `Given row with HORN that contains one DECOY card, when pointsOf was called for this card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 0,
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
    fun `Given row with bad weather that contains one DECOY card, when pointsOf was called for this card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 0,
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
    fun `Given row that contains HORN card and DECOY card, when pointsOf was called for decoy card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 0,
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
                card1,
                card2
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
    fun `Given row with horn that contains MORALE_BOOST card and DECOY card, when pointsOf was called for decoy card, then should return 0`() {
        //Given
        val card1 = Card(
            points = 0,
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
                card1,
                card2
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