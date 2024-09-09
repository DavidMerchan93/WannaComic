package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.model.ComicDetailModel
import com.davidmerchan.domain.repository.LocalComicDetailRepository
import javax.inject.Inject

class AddComicToCartUseCase @Inject constructor(
    private val localComicDetailRepository: LocalComicDetailRepository
) {
    suspend operator fun invoke(comicDetailModel: ComicDetailModel) {
        localComicDetailRepository.addToCart(comicDetailModel)
    }
}
