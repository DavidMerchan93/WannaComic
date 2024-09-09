package com.davidmerchan.domain.useCase

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.ShoppingCartItem
import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import javax.inject.Inject

class GetShoppingCartUseCase @Inject constructor(
    private val shoppingCartDataRepository: ShoppingCartDataRepository
) {
    suspend operator fun invoke(): Resource<List<ShoppingCartItem>> =
        shoppingCartDataRepository.getShoppingCart()
}
