package com.davidmerchan.data.models.homeComics

import com.davidmerchan.domain.model.HomeComicsListDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeComicsDataResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("limit")
    val limit: Int,
    @SerialName("offset")
    val offset: Int,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("total")
    val total: Int
) {
    fun toDomain(): HomeComicsListDomain {
        return HomeComicsListDomain(
            comics = results.map { it.toDomain() }
        )
    }
}
