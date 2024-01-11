package com.dowgalolya.gwenthelper.entities

import com.dowgalolya.gwenthelper.enums.CardsRowType
import com.dowgalolya.gwenthelper.enums.Winner
import org.junit.Assert.*
import org.junit.Test

class GameDataTest {

    //region Winner Tests

    @Test
    fun `Given GameData that contains two empty PlayerData,when winner counted then winner is a TIE`() {
        //Given
        val playerData1 = PlayerData()
        val playerData2 = PlayerData()
        val gameData = GameData(playerData1, playerData2)

        //When
        val result = gameData.winner

        //Then

        assertEquals(Winner.TIE, result)
    }

    @Test
    fun `Given GameData that contains two PlayerData - first with 10 points, second with 0 points, when winner counted, then winner is FIRST`() {
        //Given
        val card1 = Card(
            points = 10,
            abilities = emptyList()
        )

        val card2 = Card(
            points = 0,
            abilities = emptyList()
        )

        val playerData1 = PlayerData()

        val playerData2 = PlayerData()

        playerData1.cardsRows.getValue(CardsRowType.SIEGE).apply {
            cards = listOf(
                card1
            )
        }
        playerData2.cardsRows.getValue(CardsRowType.SIEGE).apply {
            cards = listOf(
                card2
            )
        }
        val gameData = GameData(playerData1, playerData2)

        //When
        val result = gameData.winner

        //Then

        assertEquals(Winner.FIRST, result)
    }

    @Test
    fun `Given GameData that contains two PlayerData - first with 0 points, second with 10 points, when winner counted, then winner is SECOND`() {
        //Given
        val card1 = Card(
            points = 0,
            abilities = emptyList()
        )

        val card2 = Card(
            points = 10,
            abilities = emptyList()
        )

        val playerData1 = PlayerData()

        val playerData2 = PlayerData()

        playerData1.cardsRows.getValue(CardsRowType.SIEGE).apply {
            cards = listOf(
                card1
            )
        }
        playerData2.cardsRows.getValue(CardsRowType.SIEGE).apply {
            cards = listOf(
                card2
            )
        }
        val gameData = GameData(playerData1, playerData2)

        //When
        val result = gameData.winner

        //Then

        assertEquals(Winner.SECOND, result)
    }

    @Test
    fun `Given GameData that contains two PlayerData with equal points, when winner counted, then winner is TIE`() {
        //Given
        val card1 = Card(
            points = 10,
            abilities = emptyList()
        )

        val card2 = Card(
            points = 10,
            abilities = emptyList()
        )

        val playerData1 = PlayerData()

        val playerData2 = PlayerData()

        playerData1.cardsRows.getValue(CardsRowType.SIEGE).apply {
            cards = listOf(
                card1
            )
        }
        playerData2.cardsRows.getValue(CardsRowType.SIEGE).apply {
            cards = listOf(
                card2
            )
        }
        val gameData = GameData(playerData1, playerData2)

        //When
        val result = gameData.winner

        //Then
        assertEquals(Winner.TIE, result)

    }
    //endregion
}