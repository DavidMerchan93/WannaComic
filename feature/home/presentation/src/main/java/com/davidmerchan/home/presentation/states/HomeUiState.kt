package com.davidmerchan.home.presentation.states

import com.davidmerchan.domain.entity.homeComic.HomeComicsListDomain.HomeComic

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: List<HomeComic>? = null,
    val error: String? = null
)
