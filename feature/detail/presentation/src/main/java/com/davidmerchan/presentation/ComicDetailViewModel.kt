package com.davidmerchan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.ComicDetailModel
import com.davidmerchan.domain.useCase.AddComicToCartUseCase
import com.davidmerchan.domain.useCase.GetComicDetailUseCase
import com.davidmerchan.domain.useCase.RemoveComicFromCartUseCase
import com.davidmerchan.presentation.intent.ComicDetailUiIntent
import com.davidmerchan.presentation.state.ComicDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val getComicDetailUseCase: GetComicDetailUseCase,
    private val addToCartUseCase: AddComicToCartUseCase,
    private val removeComicFromCartUseCase: RemoveComicFromCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ComicDetailUiState())
    val uiState: StateFlow<ComicDetailUiState> = _uiState.asStateFlow()

    fun handleEvent(event: ComicDetailUiIntent) {
        when (event) {
            is ComicDetailUiIntent.AddToCart -> addToCart(event.comic)
            is ComicDetailUiIntent.AddToFavorites -> addToFavorites(event.comic.id)
            is ComicDetailUiIntent.LoadComicDetail -> loadComicDetail(event.comicId)
            is ComicDetailUiIntent.RemoveFromCart -> removeFromCart(event.comicId)
            is ComicDetailUiIntent.RemoveFromFavorites -> removeToFavorites(event.comicId)
        }
    }

    private fun loadComicDetail(comicId: Long) {
        viewModelScope.launch {
            when (val result = getComicDetailUseCase(comicId)) {
                is Resource.Error -> {
                    _uiState.value = ComicDetailUiState(error = result.message)
                }

                is Resource.Success -> {
                    _uiState.value = ComicDetailUiState(comicData = result.result)
                }

                else -> _uiState.value = ComicDetailUiState(isLoading = true)
            }
        }
    }

    private fun addToCart(comicDetailModel: ComicDetailModel) {
        viewModelScope.launch {
            addToCartUseCase(comicDetailModel)
        }
    }

    private fun removeFromCart(id: Long) {
        viewModelScope.launch {
            removeComicFromCartUseCase(id)
        }
    }

    private fun addToFavorites(id: Long) {

    }

    private fun removeToFavorites(id: Long) {

    }

}
