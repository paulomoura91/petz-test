package com.paulomoura.petztest.cards.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paulomoura.petztest.cards.model.dto.Card
import com.paulomoura.petztest.cards.model.dto.CardDTO
import com.paulomoura.petztest.cards.model.dto.SetDTO
import com.paulomoura.petztest.cards.model.repository.CardsRepository
import com.paulomoura.petztest.commons.Response
import com.paulomoura.petztest.testutil.MainCoroutineRule
import com.paulomoura.petztest.testutil.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CardsViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule val mainCoroutineRule = MainCoroutineRule()

    private val cardsRepository: CardsRepository = mockk()
    private val cardsViewModel = CardsViewModel(cardsRepository)

    @Test
    fun `Request Card Sets from repository, update livedata with loading, then with success response`() = runTest {
        val sets = SetDTO(listOf("set1", "set2"))
        coEvery { cardsRepository.getSets() } returns sets

        cardsViewModel.getSets()

        var response = cardsViewModel.cardSetsLiveData.getOrAwaitValue()
        assert(response is Response.Loading)

        advanceUntilIdle()

        response = cardsViewModel.cardSetsLiveData.getOrAwaitValue()
        assert(response is Response.Success)
        assert(response.data == sets.sets)
    }

    @Test
    fun `Request Card Sets from repository, update livedata with loading, then with error response`() = runTest {
        coEvery { cardsRepository.getSets() } throws  Exception()

        cardsViewModel.getSets()

        var response = cardsViewModel.cardSetsLiveData.getOrAwaitValue()
        assert(response is Response.Loading)

        advanceUntilIdle()

        response = cardsViewModel.cardSetsLiveData.getOrAwaitValue()
        assert(response is Response.Error)
    }

    @Test
    fun `Request Cards from repository, update livedata with loading, then with success response`() = runTest {
        val cardDTOs = getFakeCardDTOs()
        coEvery { cardsRepository.getCardsInASet(any()) } returns cardDTOs

        cardsViewModel.getCardsInASet("set")

        var response = cardsViewModel.cardsLiveData.getOrAwaitValue()
        assert(response is Response.Loading)

        advanceUntilIdle()

        response = cardsViewModel.cardsLiveData.getOrAwaitValue()
        assert(response is Response.Success)
        assert(response.data == getFakeCards())
    }

    @Test
    fun `Request Cards from repository, update livedata with loading, then with error response`() = runTest {
        coEvery { cardsRepository.getCardsInASet(any()) } throws Exception()

        cardsViewModel.getCardsInASet("set")

        var response = cardsViewModel.cardsLiveData.getOrAwaitValue()
        assert(response is Response.Loading)

        advanceUntilIdle()

        response = cardsViewModel.cardsLiveData.getOrAwaitValue()
        assert(response is Response.Error)
    }

    private fun getFakeCardDTOs() = listOf(
        CardDTO(
            cardId = "cardId",
            name = "name",
            cardSet = "cardSet",
            type = "type",
            faction = "faction",
            rarity = "rarity",
            cost = 1,
            attack = 1,
            health = 1,
            text = "text",
            flavor = "flavor",
            artist = "artist",
            collectible = true,
            elite = true,
            race = "race",
            img = "img",
            imgGold = "imgGold",
            locale = "locale"
        ),
        CardDTO(
            cardId = "cardId2",
            name = "name2",
            cardSet = "cardSet2",
            type = "type2",
            faction = "faction2",
            rarity = "rarity2",
            cost = 2,
            attack = 2,
            health = 2,
            text = "text2",
            flavor = "flavor2",
            artist = "artist2",
            collectible = true,
            elite = true,
            race = "race2",
            img = "img2",
            imgGold = "imgGold2",
            locale = "locale2"
        )
    )

    private fun getFakeCards() = listOf(
        Card(
            cardId = "cardId",
            img = "img",
            name = "name",
            cardSet = "cardSet",
            type = "type",
            faction = "faction",
            rarity = "rarity",
            cost = 1,
            attack = 1,
            health = 1,
            text = "text",
            flavor = "flavor"
        ),
        Card(
            cardId = "cardId2",
            img = "img2",
            name = "name2",
            cardSet = "cardSet2",
            type = "type2",
            faction = "faction2",
            rarity = "rarity2",
            cost = 2,
            attack = 2,
            health = 2,
            text = "text2",
            flavor = "flavor2"
        )
    )
}