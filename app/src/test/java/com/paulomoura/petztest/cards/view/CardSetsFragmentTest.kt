package com.paulomoura.petztest.cards.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paulomoura.petztest.R
import com.paulomoura.petztest.cards.model.dto.SetDTO
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
class CardSetsFragmentTest {

    @get:Rule val hiltAndroidRule = HiltAndroidRule(this)
    @get:Rule val mainCoroutineRule = MainCoroutineRule()

    private val cardsRepository: CardsRepository = mockk(relaxed = true)
    @BindValue val viewModel = CardsViewModel(cardsRepository)

    @Before
    fun setup() = hiltAndroidRule.inject()

    @Test
    fun `Call 'getSets' endpoint with success and validate the UI`() {
        coEvery { cardsRepository.getSets() } returns SetDTO(listOf("set1", "set2", "set3", "set4"))
        launchFragmentInHiltContainer<CardSetsFragment> {
            runTest {
                advanceUntilIdle()
                viewModel.getSets()
                onView(withId(R.id.loading_view)).check(matches(isDisplayed()))

                advanceUntilIdle()

                onView(withId(R.id.loading_view)).check(matches(not(isDisplayed())))
                onView(withId(R.id.list)).check(matches(hasChildCount(4)))
            }
        }
    }

    @Test
    fun `Call 'getSets' endpoint with error and validate the UI`() {
        coEvery { cardsRepository.getSets() } throws Exception("network error")
        launchFragmentInHiltContainer<CardSetsFragment> {
            runTest {
                advanceUntilIdle()
                viewModel.getSets()
                onView(withId(R.id.loading_view)).check(matches(isDisplayed()))

                advanceUntilIdle()

                onView(withId(R.id.loading_view)).check(matches(not(isDisplayed())))
                assert("Error: network error" == ShadowToast.getTextOfLatestToast())
            }
        }
    }
}