package com.davidmerchan.domain.repository

import com.davidmerchan.domain.model.ComicDetailModel

interface LocalComicDetailRepository {
    suspend fun getComicDetail(comicId: Long): ComicDetailModel?
    suspend fun addToCart(comicDetailModel: ComicDetailModel)
    suspend fun removeFromCart(comicId: Long)
}
