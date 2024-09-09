package com.davidmerchan.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicDetailResult(
    @SerialName("characters")
    val characters: Characters,
    @SerialName("creators")
    val creators: Creators,
    @SerialName("description")
    val description: String,
    @SerialName("diamondCode")
    val diamondCode: String,
    @SerialName("digitalId")
    val digitalId: Int,
    @SerialName("ean")
    val ean: String,
    @SerialName("format")
    val format: String,
    @SerialName("id")
    val id: Int,
    @SerialName("isbn")
    val isbn: String,
    @SerialName("issn")
    val issn: String,
    @SerialName("issueNumber")
    val issueNumber: Int,
    @SerialName("modified")
    val modified: String,
    @SerialName("pageCount")
    val pageCount: Int,
    @SerialName("prices")
    val prices: List<Price>,
    @SerialName("resourceURI")
    val resourceURI: String,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail,
    @SerialName("title")
    val title: String,
    @SerialName("upc")
    val upc: String,
)