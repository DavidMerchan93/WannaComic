package com.davidmerchan.data.models.homeComics


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(
    @SerialName("extension")
    val extension: String,
    @SerialName("path")
    val path: String
)