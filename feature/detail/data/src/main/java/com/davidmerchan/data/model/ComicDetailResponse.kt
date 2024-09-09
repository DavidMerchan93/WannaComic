package com.davidmerchan.data.model

import com.davidmerchan.domain.model.ComicDetailModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicDetailResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("limit")
    val limit: Int,
    @SerialName("offset")
    val offset: Int,
    @SerialName("results")
    val results: List<ComicDetailResult>,
    @SerialName("total")
    val total: Int
) {
    fun toDomain(copyright: String): ComicDetailModel? = results.firstOrNull()?.run {
        ComicDetailModel(
            id = id.toLong(),
            copyright = copyright,
            description = description,
            isAvailable = true,
            pages = pageCount,
            price = prices.firstOrNull()?.price ?: .0,
            thumbnail = thumbnail.path.plus(".").plus(thumbnail.extension),
            title = title,
            author = creators.items.map { it.name }
        )
    }
}
