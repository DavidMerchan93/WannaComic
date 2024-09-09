package com.davidmerchan.presentation

import com.davidmerchan.domain.model.ShoppingCartItem

data class ShoppingCartUiState(
    val isLoading: Boolean = false,
    val cartItems: List<ShoppingCartItem> = emptyList(),
    val isCartEmpty: Boolean = false,
    val error: String? = null
)
