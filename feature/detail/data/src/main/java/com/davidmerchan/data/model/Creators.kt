package com.davidmerchan.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Creators(
    @SerialName("available")
    val available: Int,
    @SerialName("items")
    val items: List<CreatorItem>
) {
    
    @Serializable
    data class CreatorItem(
        val name: String,
        val resourceURI: String,
        val role: String
    )
}
