package com.davidmerchan.presentation.intent

import com.davidmerchan.domain.model.ComicDetailModel

sealed class ComicDetailUiIntent {
    data class LoadComicDetail(val comicId: Long) : ComicDetailUiIntent()
    data class AddToCart(val comic: ComicDetailModel) : ComicDetailUiIntent()
    data class RemoveFromCart(val comicId: Long) : ComicDetailUiIntent()
    data class RemoveFromFavorites(val comicId: Long) : ComicDetailUiIntent()
    data class AddToFavorites(val comic: ComicDetailModel) : ComicDetailUiIntent()
}
