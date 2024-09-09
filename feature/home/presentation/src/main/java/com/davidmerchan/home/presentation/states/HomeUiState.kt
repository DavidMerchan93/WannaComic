package com.davidmerchan.home.presentation.states

import com.davidmerchan.domain.model.HomeComicsListDomain.HomeComic

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: List<HomeComic>? = null,
    val error: String? = null
)
