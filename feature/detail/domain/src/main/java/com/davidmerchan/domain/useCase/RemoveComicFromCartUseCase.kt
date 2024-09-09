package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.repository.LocalComicDetailRepository
import javax.inject.Inject

class RemoveComicFromCartUseCase @Inject constructor(
    private val localComicDetailRepository: LocalComicDetailRepository
) {
    suspend operator fun invoke(id: Long) {
        localComicDetailRepository.removeFromCart(id)
    }
}
