package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import javax.inject.Inject

class DeleteComicFromCartUseCase @Inject constructor(
    private val shoppingCartDataRepository: ShoppingCartDataRepository
) {
    suspend operator fun invoke(comicId: Long): Boolean =
        shoppingCartDataRepository.removeComicFromCart(comicId)
}
