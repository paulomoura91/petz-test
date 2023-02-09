package com.paulomoura.petztest.cards.model.dto

import com.google.gson.annotations.SerializedName

data class SetDTO (
    @SerializedName("sets") val sets: List<String>
)