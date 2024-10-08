package com.davidmerchan.data.models.homeComics


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Price(
    @SerialName("price")
    val price: Double,
    @SerialName("type")
    val type: String
)