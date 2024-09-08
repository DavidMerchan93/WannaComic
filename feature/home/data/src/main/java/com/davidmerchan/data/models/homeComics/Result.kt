package com.davidmerchan.data.models.homeComics

import com.davidmerchan.domain.entity.homeComic.HomeComicsListDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("id")
    val id: Long,
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
    val images: List<Image>
) {
    fun toDomain(): HomeComicsListDomain.HomeComic {
        return HomeComicsListDomain.HomeComic(
            id = id,
            pages = pageCount,
            title = title,
            thumbnail = thumbnail.path.plus(".").plus(thumbnail.extension)
        )
    }
}
