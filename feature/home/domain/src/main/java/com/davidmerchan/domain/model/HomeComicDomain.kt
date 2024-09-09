package com.davidmerchan.domain.model

data class HomeComicsListDomain(
    val comics: List<HomeComic>
) {
    data class HomeComic(
        val id: Long,
        val title: String,
        val pages: Int,
        val thumbnail: String,
    )
}
