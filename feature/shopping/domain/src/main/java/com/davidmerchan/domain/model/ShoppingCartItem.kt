package com.davidmerchan.domain.model

data class ShoppingCartItem(
    val comicId: Long,
    val title: String,
    val image: String,
    val totalPrice: Double,
)
