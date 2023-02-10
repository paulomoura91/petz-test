package com.paulomoura.petztest.cards.model.dto

import org.junit.Test

class CardDTOTest {

    private val fakeCardDTO = CardDTO(
        cardId = "cardId",
        name = "name",
        cardSet = "cardSet",
        type = "type",
        faction = "faction",
        rarity = "rarity",
        cost = 0,
        attack = 0,
        health = 0,
        text = "text",
        flavor = "flavor",
        artist = "artist",
        collectible = true,
        elite = true,
        race = "race",
        img = "img",
        imgGold = "imgGold",
        locale = "locale"
    )

    private val fakeConvertedCard = Card(
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
    fun `Check whether DTO is being converted correctly`() {
        val card = fakeCardDTO.toCard()
        assert(card == fakeConvertedCard)
    }
}