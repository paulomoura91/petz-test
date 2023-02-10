package com.paulomoura.petztest.cards.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CardDTO (
    @SerializedName("cardId") val cardId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("cardSet") val cardSet: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("faction") val faction: String?,
    @SerializedName("rarity") val rarity: String?,
    @SerializedName("cost") val cost: Int?,
    @SerializedName("attack") val attack: Int?,
    @SerializedName("health") val health: Int?,
    @SerializedName("text") val text: String?,
    @SerializedName("flavor") val flavor: String?,
    @SerializedName("artist") val artist: String?,
    @SerializedName("collectible") val collectible: Boolean?,
    @SerializedName("elite") val elite: Boolean?,
    @SerializedName("race") val race: String?,
    @SerializedName("img") val img: String?,
    @SerializedName("imgGold") val imgGold: String?,
    @SerializedName("locale") val locale: String?
)

@Parcelize
data class Card (
    val cardId: String?,
    val img: String?,
    val name: String?,
    val flavor: String?,
    val text: String?,
    val cardSet: String?,
    val type: String?,
    val faction: String?,
    val rarity: String?,
    val attack: Int?,
    val cost: Int?,
    val health: Int?
) : Parcelable

fun CardDTO.toCard() =
    Card(
        cardId = cardId,
        img = img,
        name = name,
        flavor = flavor,
        text = text,
        cardSet = cardSet,
        type = type,
        faction = faction,
        rarity = rarity,
        attack = attack,
        cost = cost,
        health = health
    )