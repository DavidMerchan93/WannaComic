package com.davidmerchan.data.models.homeComics


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Date(
    @SerialName("date")
    val date: String,
    @SerialName("type")
    val type: String
)