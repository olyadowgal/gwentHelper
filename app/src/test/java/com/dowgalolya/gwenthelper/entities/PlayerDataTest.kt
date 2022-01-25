package com.dowgalolya.gwenthelper.entities

import com.dowgalolya.gwenthelper.enums.Ability
import com.dowgalolya.gwenthelper.enums.CardsRowType
import org.junit.Assert.*
import org.junit.Test

class PlayerDataTest {

    //region totalPoints tests

    @Test
    fun `Given playerData with empty CardRows, when totalPoints called for this playerData, then return 0`() {
        //Given
        val playerData = PlayerData()
        playerData.cardsRows.getValue(CardsRowType.SIEGE).cards = emptyList()
        playerData.cardsRows.getValue(CardsRowType.CLOSE_COMBAT).cards = emptyList()
        playerData.cardsRows.getValue(CardsRowType.LONG_RANGE).cards = emptyList()

        //When
        val result = playerData.totalPoints

        //Then
        assertEquals(0, result)
    }

    @Test
    fun `Given playerData with CardRows contains regular card, when totalPoints called for this playerData, then return total points for all three CardRows`() {
        //Given
        val card1 = Card(
            points = 5,
            abilities = emptyList()
        )

        val card2 = Card(
            points = 10,
            abilities = emptyList()
        )

        val card3 = Card(
            points = 15,
            abilities = emptyList()
        )

        val playerData = PlayerData()
        playerData.cardsRows.getValue(CardsRowType.SIEGE).apply {
            cards = listOf(
                card1
            )
        }
        playerData.cardsRows.getValue(CardsRowType.CLOSE_COMBAT).apply {
            cards = listOf(
                card2
            )
        }
        playerData.cardsRows.getValue(CardsRowType.LONG_RANGE).apply {
            cards = listOf(
                card3
            )
        }

        //When
        val result = playerData.totalPoints

        //Then
        assertEquals(30, result)
    }

    //endregion

    //region minusLive Tests

    @Test
    fun `Given playerData with all lives when call minusLive three times then lives must be 0`() {
        //Given

        val playerData = PlayerData()

        //When

        playerData.minusLive()
        playerData.minusLive()
        playerData.minusLive()

        val result = playerData.lives

        //Then

        assertEquals(0, result)
    }

    @Test
    fun `Given playerData with all lives when call minusLive one times then lives must be 1`() {
        //Given

        val playerData = PlayerData()

        //When

        playerData.minusLive()

        val result = playerData.lives

        //Then

        assertEquals(1, result)
    }

    //endregion
}