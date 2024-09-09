package com.davidmerchan.domain.useCase

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.ComicDetailModel
import com.davidmerchan.domain.repository.ComicDetailRepository
import javax.inject.Inject

class GetComicDetailUseCase @Inject constructor(
    private val comicDetailRepository: ComicDetailRepository,
    private val getLocalComicUseCase: GetLocalComicUseCase
) {
    suspend operator fun invoke(id: Long): Resource<ComicDetailModel?> {
        val comicDetail = comicDetailRepository.getComicDetail(id)
        val localComic = getLocalComicUseCase(id)

        return when(comicDetail) {
            is Resource.Error -> comicDetail
            Resource.Loading -> comicDetail
            is Resource.Success -> {
                val comic = comicDetail.result
                Resource.Success(comic?.copy(hasInCart = localComic != null))
            }
        }
    }
}
