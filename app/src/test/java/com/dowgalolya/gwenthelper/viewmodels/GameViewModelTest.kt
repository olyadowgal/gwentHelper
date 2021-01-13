package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dowgalolya.gwenthelper.adapters.CardRowAdapter
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.enums.Ability
import com.dowgalolya.gwenthelper.enums.CardsRowType
import com.dowgalolya.gwenthelper.enums.Player
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GameViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    //region Mocks

    private val app = mockk<Application>()

    private val cardRowAdapterCloseCombat: CardRowAdapter = mockk()
    private val cardRowAdapterLongRange: CardRowAdapter = mockk()
    private val cardRowAdapterSiege: CardRowAdapter = mockk()
    private val cardRowAdapterFactory = mockk<CardRowAdapter.Factory> {
        every {
            create(
                match { it.type == CardsRowType.CLOSE_COMBAT },
                any()
            )
        } returns cardRowAdapterCloseCombat

        every {
            create(
                match { it.type == CardsRowType.LONG_RANGE },
                any()
            )
        } returns cardRowAdapterLongRange

        every {
            create(
                match { it.type == CardsRowType.SIEGE },
                any()
            )
        } returns cardRowAdapterSiege
    }
    //endregion

    private val vm by lazy { GameViewModel(app, cardRowAdapterFactory) }

    //region onCardAdd Tests

    @Test
    fun `Given Card, when onCardAdd was called with this card and CardsRowType CLOSE_COMBAT Then selectedPlayerData row CLOSE_COMBAT should include this card`() {
        //Given

        val card = Card(
            points = 5,
            abilities = emptyList()
        )

        //When
        vm.onCardAdd(CardsRowType.CLOSE_COMBAT, card)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.CLOSE_COMBAT]?.apply {
            this.cards = listOf(card)
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `Given Card, when onCardAdd was called with this card and CardsRowType LONG_RANGE Then selectedPlayerData row LONG_RANGE should include this card`() {
        //Given

        val card = Card(
            points = 5,
            abilities = emptyList()
        )

        //When
        vm.onCardAdd(CardsRowType.LONG_RANGE, card)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.LONG_RANGE]?.apply {
            this.cards = listOf(card)
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `Given Card, when onCardAdd was called with this card and CardsRowType SIEGE Then selectedPlayerData row SIEGE should include this card`() {
        //Given

        val card = Card(
            points = 5,
            abilities = emptyList()
        )

        //When
        vm.onCardAdd(CardsRowType.SIEGE, card)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.SIEGE]?.apply {
            this.cards = listOf(card)
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }
    //endregion

    //region onHornChecked Tests

    @Test
    fun `When onHornChecked was called with CardRowType CLOSE_COMBAT and isChecked is TRUE, then selectedPlayerData of ViewModel should include row CLOSE_COMBAT that have horn checked TRUE`() {
        //When
        vm.onHornChecked(CardsRowType.CLOSE_COMBAT, true)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.CLOSE_COMBAT]?.apply {
            this.horn = true
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onHornChecked was called with CardRowType CLOSE_COMBAT and isChecked is FALSE, then selectedPlayerData of ViewModel should include row CLOSE_COMBAT that have horn checked FALSE`() {
        //When
        vm.onHornChecked(CardsRowType.CLOSE_COMBAT, false)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.CLOSE_COMBAT]?.apply {
            this.horn = false
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onHornChecked was called with CardRowType LONG_RANGE and isChecked is TRUE, then selectedPlayerData of ViewModel should include row LONG_RANGE that have horn checked TRUE`() {
        //When
        vm.onHornChecked(CardsRowType.LONG_RANGE, true)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.LONG_RANGE]?.apply {
            this.horn = true
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onHornChecked was called with CardRowType LONG_RANGE and isChecked is FALSE, then selectedPlayerData of ViewModel should include row LONG_RANGE that have horn checked FALSE`() {
        //When
        vm.onHornChecked(CardsRowType.LONG_RANGE, false)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.LONG_RANGE]?.apply {
            this.horn = false
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onHornChecked was called with CardRowType SIEGE and isChecked is TRUE, then selectedPlayerData of ViewModel should include row SIEGE that have horn checked TRUE`() {
        //When
        vm.onHornChecked(CardsRowType.SIEGE, true)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.SIEGE]?.apply {
            this.horn = true
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onHornChecked was called with CardRowType SIEGE and isChecked is FALSE, then selectedPlayerData of ViewModel should include row SIEGE that have horn checked FALSE`() {
        //When
        vm.onHornChecked(CardsRowType.SIEGE, false)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.SIEGE]?.apply {
            this.horn = false
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    //endregion

    //region onWeatherChange Tests

    @Test
    fun `When onWeatherChange was called with CardRowType CLOSE_COMBAT and isChecked is TRUE, then selectedPlayerData of ViewModel should include row CLOSE_COMBAT that have badWeather checked TRUE`() {
        //When
        vm.onWeatherChange(CardsRowType.CLOSE_COMBAT, true)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.CLOSE_COMBAT]?.apply {
            this.badWeather = true
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onWeatherChange was called with CardRowType CLOSE_COMBAT and isChecked is FALSE, then selectedPlayerData of ViewModel should include row CLOSE_COMBAT that have badWeather checked FALSE`() {
        //When
        vm.onWeatherChange(CardsRowType.CLOSE_COMBAT, false)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.CLOSE_COMBAT]?.apply {
            this.badWeather = false
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onWeatherChange was called with CardRowType LONG_RANGE and isChecked is TRUE, then selectedPlayerData of ViewModel should include row LONG_RANGE that have badWeather checked TRUE`() {
        //When
        vm.onWeatherChange(CardsRowType.LONG_RANGE, true)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.LONG_RANGE]?.apply {
            this.badWeather = true
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onWeatherChange was called with CardRowType LONG_RANGE and isChecked is FALSE, then selectedPlayerData of ViewModel should include row LONG_RANGE that have badWeather checked FALSE`() {
        //When
        vm.onWeatherChange(CardsRowType.LONG_RANGE, false)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.LONG_RANGE]?.apply {
            this.badWeather = false
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onWeatherChange was called with CardRowType SIEGE and isChecked is TRUE, then selectedPlayerData of ViewModel should include row SIEGE that have badWeather checked TRUE`() {
        //When
        vm.onWeatherChange(CardsRowType.SIEGE, true)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.SIEGE]?.apply {
            this.badWeather = true
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }

    @Test
    fun `When onWeatherChange was called with CardRowType SIEGE and isChecked is FALSE, then selectedPlayerData of ViewModel should include row SIEGE that have badWeather checked FALSE`() {
        //When
        vm.onWeatherChange(CardsRowType.SIEGE, false)

        //Then

        val playerData = PlayerData()
        playerData.cardsRows[CardsRowType.SIEGE]?.apply {
            this.badWeather = false
        }

        assertEquals(playerData, vm.selectedPlayerData.value)
    }
    //endregion

    //region onDeleteClicked Tests

    @Test
    fun `Given card that don't belong to row CLOSE_COMBAT, when onDeleteClicked called with this card and CardRowType CLOSE_COMBAT then content of CLOSE_COMBAT row doesn't change`() {
        //Given
        val deleteCard = Card(
            points = 5,
            abilities = emptyList()
        )

        val addCard = Card(
            points = 5,
            abilities = emptyList()
        )
        vm.onCardAdd(CardsRowType.CLOSE_COMBAT, addCard)

        //When
        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.let {
            vm.onDeleteClicked(
                it, deleteCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.CLOSE_COMBAT,
                cards = listOf(addCard)
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)
        )
    }

    @Test
    fun `Given card that don't belong to row LONG_RANGE, when onDeleteClicked called with this card and CardRowType LONG_RANGE then content of LONG_RANGE row doesn't change`() {
        //Given
        val deleteCard = Card(
            points = 5,
            abilities = emptyList()
        )

        val addCard = Card(
            points = 5,
            abilities = emptyList()
        )
        vm.onCardAdd(CardsRowType.LONG_RANGE, addCard)

        //When
        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.LONG_RANGE)?.let {
            vm.onDeleteClicked(
                it, deleteCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.LONG_RANGE,
                cards = listOf(addCard)
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.LONG_RANGE)
        )
    }

    @Test
    fun `Given card that don't belong to row SIEGE, when onDeleteClicked called with this card and CardRowType SIEGE then content of SIEGE row doesn't change`() {
        //Given
        val deleteCard = Card(
            points = 5,
            abilities = emptyList()
        )

        val addCard = Card(
            points = 5,
            abilities = emptyList()
        )
        vm.onCardAdd(CardsRowType.SIEGE, addCard)

        //When
        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.SIEGE)?.let {
            vm.onDeleteClicked(
                it, deleteCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.SIEGE,
                cards = listOf(addCard)
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.SIEGE)
        )
    }

    @Test
    fun `Given card that belong to row CLOSE_COMBAT, when onDeleteClicked called with this card and CardRowType CLOSE_COMBAT then CLOSE_COMBAT row will be empty`() {
        //Given
        val deleteCard = Card(
            points = 5,
            abilities = emptyList()
        )
        vm.onCardAdd(CardsRowType.CLOSE_COMBAT, deleteCard)

        //When
        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.let {
            vm.onDeleteClicked(
                it, deleteCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.CLOSE_COMBAT,
                cards = emptyList()
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)
        )
    }

    @Test
    fun `Given card that belong to row LONG_RANGE, when onDeleteClicked called with this card and CardRowType LONG_RANGE then content of LONG_RANGE row will be empty`() {
        //Given
        val deleteCard = Card(
            points = 5,
            abilities = emptyList()
        )
        vm.onCardAdd(CardsRowType.LONG_RANGE, deleteCard)

        //When
        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.LONG_RANGE)?.let {
            vm.onDeleteClicked(
                it, deleteCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.LONG_RANGE,
                cards = emptyList()
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.LONG_RANGE)
        )
    }

    @Test
    fun `Given card that belong to row SIEGE, when onDeleteClicked called with this card and CardRowType SIEGE then content of SIEGE row will be empty`() {
        //Given
        val deleteCard = Card(
            points = 5,
            abilities = emptyList()
        )
        vm.onCardAdd(CardsRowType.SIEGE, deleteCard)

        //When
        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.SIEGE)?.let {
            vm.onDeleteClicked(
                it, deleteCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.SIEGE,
                cards = emptyList()
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.SIEGE)
        )
    }

    //endregion

    //region onCardEdit Tests

    @Test
    fun `Given card that added to CLOSE_COMBAT row, when card changed and called onEditCalled for CLOSE_COMBAT and edited card then card points and abilities will change`() {
        //Given
        val startCard = Card(
            points = 5,
            abilities = emptyList()
        )


        vm.onCardAdd(CardsRowType.CLOSE_COMBAT, startCard)

        //When

        val editedCard = Card(
            cardId = startCard.cardId,
            points = 10,
            abilities = listOf(Ability.HERO)
        )

        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.let {
            vm.onCardEdit(
                it, editedCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.CLOSE_COMBAT,
                cards = listOf(editedCard)
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)
        )
    }

    @Test
    fun `Given card that added to LONG_RANGE row, when card changed and called onEditCalled for LONG_RANGE and edited card then card points and abilities will change`() {
        //Given
        val startCard = Card(
            points = 5,
            abilities = emptyList()
        )


        vm.onCardAdd(CardsRowType.CLOSE_COMBAT, startCard)

        //When

        val editedCard = Card(
            cardId = startCard.cardId,
            points = 10,
            abilities = listOf(Ability.HERO)
        )

        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.let {
            vm.onCardEdit(
                it, editedCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.CLOSE_COMBAT,
                cards = listOf(editedCard)
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)
        )
    }

    @Test
    fun `Given card that added to SIEGE row, when card changed and called onEditCalled for SIEGE and edited card then card points and abilities will change`() {
        //Given
        val startCard = Card(
            points = 5,
            abilities = emptyList()
        )


        vm.onCardAdd(CardsRowType.SIEGE, startCard)

        //When

        val editedCard = Card(
            cardId = startCard.cardId,
            points = 10,
            abilities = listOf(Ability.HERO)
        )

        vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.SIEGE)?.let {
            vm.onCardEdit(
                it, editedCard
            )
        }

        //Then
        assertEquals(
            CardsRow(
                type = CardsRowType.SIEGE,
                cards = listOf(editedCard)
            ),
            vm.selectedPlayerData.value?.cardsRows?.get(CardsRowType.SIEGE)
        )
    }
    //endregion

    //region onPassClicked Tests

    @Test
    fun `When FIRST Player has less points then SECOND and onPassClicked was called then FIRST player lives will decreased by 1`() {
        //When
        vm.gameData.value?.secondPlayerData?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.apply {
            cards = listOf(Card(points = 10, abilities = emptyList()))
        }
        vm.onRoundEnds()
        //Then
        assertEquals(1, vm.gameData.value?.firstPlayerData?.lives)
    }

    @Test
    fun `When SECOND Player has less points then FIRST and onPassClicked was called then SECOND player lives will decreased by 1`() {
        //When
        vm.gameData.value?.firstPlayerData?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.apply {
            cards = listOf(Card(points = 10, abilities = emptyList()))
        }
        vm.onRoundEnds()
        //Then
        assertEquals(1, vm.gameData.value?.secondPlayerData?.lives)
    }

    @Test
    fun `When SECOND Player has less points then FIRST and onPassClicked was called two times then SECOND player lives will be 0`() {
        //When
        vm.gameData.value?.firstPlayerData?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.apply {
            cards = listOf(Card(points = 10, abilities = emptyList()))
        }
        vm.onRoundEnds()
        vm.gameData.value?.firstPlayerData?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.apply {
            cards = listOf(Card(points = 10, abilities = emptyList()))
        }
        vm.onRoundEnds()
        //Then
        assertEquals(0, vm.gameData.value?.secondPlayerData?.lives)
    }

    @Test
    fun `When FIRST Player has less points then SECOND and onPassClicked was called two times then FIRST player lives will be 0`() {
        //When
        vm.gameData.value?.secondPlayerData?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.apply {
            cards = listOf(Card(points = 10, abilities = emptyList()))
        }
        vm.onRoundEnds()
        vm.gameData.value?.secondPlayerData?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.apply {
            cards = listOf(Card(points = 10, abilities = emptyList()))
        }
        vm.onRoundEnds()
        //Then
        assertEquals(0, vm.gameData.value?.firstPlayerData?.lives)
    }

    @Test
    fun `When FIRST Player has less points then SECOND, onPassClicked was called, SECOND Player has less points FIRST then and onPassClicked was called then BOTH players lives will be 0`() {
        //When
        vm.gameData.value?.secondPlayerData?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.apply {
            cards = listOf(Card(points = 10, abilities = emptyList()))
        }
        vm.onRoundEnds()
        vm.gameData.value?.firstPlayerData?.cardsRows?.get(CardsRowType.CLOSE_COMBAT)?.apply {
            cards = listOf(Card(points = 10, abilities = emptyList()))
        }
        vm.onRoundEnds()
        //Then
        assertEquals(1, vm.gameData.value?.firstPlayerData?.lives)
        assertEquals(1, vm.gameData.value?.secondPlayerData?.lives)
    }
    //endregion

    //region onUserClicked Tests

    @Test
    fun `When onUserClicked was called with Player FIRST then selectedPlayer value should be Player FIRST`() {
        //When
        vm.onUserClicked(Player.FIRST)
        //Then
        assertEquals(Player.FIRST, vm.selectedPlayer.value)
    }

    @Test
    fun `When onUserClicked was called with Player SECOND then selectedPlayer value should be Player SECOND`() {
        //When
        vm.onUserClicked(Player.SECOND)
        //Then
        assertEquals(Player.SECOND, vm.selectedPlayer.value)
    }

    //endregion
}