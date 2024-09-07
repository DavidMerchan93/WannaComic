package com.davidmerchan.data.repository

import com.davidmerchan.core.model.Resource
import com.davidmerchan.data.models.homeComics.HomeComicsDataResponse
import com.davidmerchan.domain.entity.homeComic.HomeComicDomain
import com.davidmerchan.domain.repository.HomeComicsRepository
import com.davidmerchan.network.ApiManager
import javax.inject.Inject

class HomeComicsRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager
) : HomeComicsRepository {
    override suspend fun getComicsHome(): Resource<HomeComicDomain> {
        val response = apiManager.get<HomeComicsDataResponse>("comics")
        return when {
            response.isSuccess -> {
                response.getOrNull()?.let {
                    Resource.Success(it.toDomain())
                } ?: run {
                    Resource.Error("General Error")
                }
            }

            response.isFailure -> {
                Resource.Error(response.exceptionOrNull()?.message ?: "General Error")
            }

            else -> {
                Resource.Loading
            }
        }
    }
}
