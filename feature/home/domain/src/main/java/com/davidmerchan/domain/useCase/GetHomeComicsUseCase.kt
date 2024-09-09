package com.davidmerchan.domain.useCase

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.HomeComicsListDomain
import com.davidmerchan.domain.repository.HomeComicsRepository
import javax.inject.Inject

class GetHomeComicsUseCase @Inject constructor(
    private val homeComicsRepository: HomeComicsRepository
) {
    suspend operator fun invoke(): Resource<HomeComicsListDomain> =
        homeComicsRepository.getComicsHome()
}