package com.paulomoura.petztest.cards.model.network

import com.paulomoura.petztest.cards.model.dto.CardDTO
import com.paulomoura.petztest.cards.model.dto.SetDTO
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface CardsService {

    @GET("info")
    suspend fun getSets(@HeaderMap headers: Map<String, String>): SetDTO

    @GET("cards/sets/{set}")
    suspend fun getCardsInASet(@HeaderMap headers: Map<String, String>, @Path("set") set: String): List<CardDTO>
}