package com.davidmerchan.presentation

sealed class ShoppingCartUiIntent {
    data object LoadShoppingCart : ShoppingCartUiIntent()
    data class RemoveComicFromCart(val comicId: Long) : ShoppingCartUiIntent()
    data object ClearCart : ShoppingCartUiIntent()
    data object Payment : ShoppingCartUiIntent()
}
