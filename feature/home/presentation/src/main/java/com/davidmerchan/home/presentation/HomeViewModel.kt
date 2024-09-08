package com.davidmerchan.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.useCase.GetHomeComicsUseCase
import com.davidmerchan.home.presentation.intents.HomeUiIntent
import com.davidmerchan.home.presentation.states.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeComicsUseCase: GetHomeComicsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: HomeUiIntent) {
        when(intent) {
            HomeUiIntent.LoadHomeComics -> getHomeComics()
            HomeUiIntent.RefreshHomeComics -> getHomeComics()
        }
    }


    private fun getHomeComics() {
        _uiState.value = HomeUiState(isLoading = true)
        viewModelScope.launch {
            when(val result = getHomeComicsUseCase()) {
                is Resource.Error -> {
                    _uiState.value = HomeUiState(error = result.message)
                }
                is Resource.Success -> {
                    _uiState.value = HomeUiState(data = result.result.comics)
                }
                else -> {
                    _uiState.value = HomeUiState(isLoading = true)
                }
            }

        }
    }
}
