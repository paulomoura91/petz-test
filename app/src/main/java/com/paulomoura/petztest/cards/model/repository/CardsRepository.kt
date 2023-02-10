package com.paulomoura.petztest.cards.model.repository

import com.paulomoura.petztest.cards.model.network.CardsService
import javax.inject.Inject

class CardsRepository @Inject constructor(private val cardsService: CardsService) {

    suspend fun getSets() = cardsService.getSets(buildHeaders())

    suspend fun getCardsInASet(set: String) = cardsService.getCardsInASet(buildHeaders(), set)

    private fun buildHeaders() = mapOf(
        "X-RapidAPI-Key" to "1d3a0da9acmshd9a786bba2346b8p1a8f8ejsn7af0c0ba7fe7",
        "X-RapidAPI-Host" to "omgvamp-hearthstone-v1.p.rapidapi.com"
    )
}