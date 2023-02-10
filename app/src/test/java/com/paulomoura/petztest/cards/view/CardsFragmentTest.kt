package com.paulomoura.petztest.cards.view

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paulomoura.petztest.R
import com.paulomoura.petztest.cards.model.dto.CardDTO
import com.paulomoura.petztest.cards.model.repository.CardsRepository
import com.paulomoura.petztest.cards.viewmodel.CardsViewModel
import com.paulomoura.petztest.testutil.MainCoroutineRule
import com.paulomoura.petztest.testutil.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class CardsFragmentTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val cardsRepository: CardsRepository = mockk(relaxed = true)
    @BindValue
    val viewModel = CardsViewModel(cardsRepository)

    @Before
    fun setup() = hiltAndroidRule.inject()

    @Test
    fun `Call 'getCardsInASet' endpoint with success and validate the UI`() {
        coEvery { cardsRepository.getCardsInASet(any()) } returns getFakeCardDTOs()
        launchFragmentInHiltContainer<CardsFragment>(
            fragmentArgs = bundleOf("ARG_SET" to "set")
        ) {
            runTest {
                advanceUntilIdle()
                viewModel.getCardsInASet("set")
                onView(withId(R.id.loading_view)).check(matches(isDisplayed()))

                advanceUntilIdle()

                onView(withId(R.id.loading_view)).check(matches(not(isDisplayed())))
                onView(withId(R.id.list)).check(matches(ViewMatchers.hasChildCount(2)))
            }
        }
    }

    @Test
    fun `Call 'getCardsInASet' endpoint with error and validate the UI`() {
        coEvery { cardsRepository.getCardsInASet(any()) } throws Exception("network error")
        launchFragmentInHiltContainer<CardsFragment>(
            fragmentArgs = bundleOf("ARG_SET" to "set")
        ) {
            runTest {
                advanceUntilIdle()
                viewModel.getCardsInASet("set")
                onView(withId(R.id.loading_view)).check(matches(isDisplayed()))

                advanceUntilIdle()

                onView(withId(R.id.loading_view)).check(matches(not(isDisplayed())))
                assert("Error: network error" == ShadowToast.getTextOfLatestToast())
            }
        }
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
}