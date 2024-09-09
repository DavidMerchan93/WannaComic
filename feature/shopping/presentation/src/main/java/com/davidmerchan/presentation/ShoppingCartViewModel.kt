package com.davidmerchan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.useCase.GetShoppingCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val getShoppingCartUseCase: GetShoppingCartUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingCartUiState())
    val uiState: StateFlow<ShoppingCartUiState> = _uiState.asStateFlow()

    fun handleEvent(event: ShoppingCartUiIntent) {
        when(event) {
            ShoppingCartUiIntent.LoadShoppingCart -> getShoppingCart()
            is ShoppingCartUiIntent.RemoveComicFromCart -> {}
        }
    }

    private fun getShoppingCart() {
        viewModelScope.launch {
            val result = getShoppingCartUseCase()
            when(result) {
                is Resource.Error -> { println("Error: ${result.message}") }
                is Resource.Success -> { println("Success") }
                Resource.Loading -> {
                    _uiState.update {
                        ShoppingCartUiState(isLoading = true)
                    }
                }
            }
        }
    }

}
