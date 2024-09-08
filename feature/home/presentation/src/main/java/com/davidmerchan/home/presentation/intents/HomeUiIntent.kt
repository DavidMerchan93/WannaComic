package com.davidmerchan.home.presentation.intents

sealed class HomeUiIntent {
    data object LoadHomeComics: HomeUiIntent()
    data object RefreshHomeComics: HomeUiIntent()
}
