package com.davidmerchan.presentation.state

import com.davidmerchan.domain.model.ComicDetailModel

data class ComicDetailUiState(
    val isLoading: Boolean = false,
    val comicData: ComicDetailModel? = null,
    val error: String? = null
)
