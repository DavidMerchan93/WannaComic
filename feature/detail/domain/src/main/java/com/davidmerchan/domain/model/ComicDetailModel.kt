package com.davidmerchan.domain.model

data class ComicDetailModel(
    val id: Long,
    val title: String,
    val author: List<String>,
    val pages: Int,
    val thumbnail: String,
    val description: String,
    val price: Double,
    val isAvailable: Boolean,
    val copyright: String
)
