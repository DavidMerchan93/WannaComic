package com.davidmerchan.data.models.homeComics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("title")
    val title: String,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail,
    @SerialName("prices")
    val prices: List<Price>,
    @SerialName("dates")
    val dates: List<Date>,
    @SerialName("pageCount")
    val pageCount: Int,
    @SerialName("images")
    val images: List<Image>,
)
