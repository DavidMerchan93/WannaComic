package com.davidmerchan.domain.useCase

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.ComicDetailModel
import com.davidmerchan.domain.repository.ComicDetailRepository
import javax.inject.Inject

class GetComicDetailUseCase @Inject constructor(
    private val comicDetailRepository: ComicDetailRepository
) {
    suspend operator fun invoke(id: Long): Resource<ComicDetailModel?> =
        comicDetailRepository.getComicDetail(id)
}
