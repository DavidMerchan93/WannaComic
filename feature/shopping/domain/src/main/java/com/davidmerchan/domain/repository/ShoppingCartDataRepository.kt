package com.davidmerchan.domain.repository

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.ShoppingCartItem

interface ShoppingCartDataRepository {
    suspend fun getShoppingCart(): Resource<List<ShoppingCartItem>>
}
