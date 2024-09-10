package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import javax.inject.Inject

class ClearShoppingCartUseCase @Inject constructor(
    private val shoppingCartDataRepository: ShoppingCartDataRepository
) {
    suspend operator fun invoke(): Boolean =
        shoppingCartDataRepository.clearShoppingCart()
}
