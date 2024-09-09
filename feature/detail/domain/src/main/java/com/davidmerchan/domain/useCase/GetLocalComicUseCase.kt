package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.model.ComicDetailModel
import com.davidmerchan.domain.repository.LocalComicDetailRepository
import javax.inject.Inject

class GetLocalComicUseCase @Inject constructor(
    private val localComicDetailRepository: LocalComicDetailRepository
) {
    suspend operator fun invoke(id: Long): ComicDetailModel? {
        return localComicDetailRepository.getComicDetail(id)
    }
}
