package com.paulomoura.petztest.cards.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paulomoura.petztest.R
import com.paulomoura.petztest.cards.model.dto.Card
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CardDetailActivityTest {

    private val fakeCard = Card(
        cardId = "cardId",
        img = "img",
        name = "name",
        flavor = "flavor",
        text = "text",
        cardSet = "cardSet",
        type = "type",
        faction = "faction",
        rarity = "rarity",
        attack = 0,
        cost = 0,
        health = 0
    )

    @Test
    fun `Check whether card details are being shown correctly`() {
        launchActivity<CardDetailActivity>(intent = Intent(
            ApplicationProvider.getApplicationContext(),
            CardDetailActivity::class.java
        ).apply { putExtra(CardDetailActivity.CARD_EXTRA, fakeCard) }
        ).use {
            onView(withId(R.id.text_name)).check(matches(withText("name")))
            onView(withId(R.id.text_flavor)).check(matches(withText("flavor")))
            onView(withId(R.id.text_short_text)).check(matches(withText("text")))
            onView(withId(R.id.text_set)).check(matches(withText("Set: cardSet")))
            onView(withId(R.id.text_type)).check(matches(withText("Type: type")))
            onView(withId(R.id.text_faction)).check(matches(withText("Faction: faction")))
            onView(withId(R.id.text_rarity)).check(matches(withText("Rarity: rarity")))
            onView(withId(R.id.text_attack)).check(matches(withText("Attack: 0")))
            onView(withId(R.id.text_cost)).check(matches(withText("Cost: 0")))
            onView(withId(R.id.text_health)).check(matches(withText("Health: 0")))
        }
    }
}