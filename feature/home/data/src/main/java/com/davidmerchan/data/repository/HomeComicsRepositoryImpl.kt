package com.davidmerchan.data.repository

import com.davidmerchan.core.model.Resource
import com.davidmerchan.data.models.homeComics.HomeComicsDataResponse
import com.davidmerchan.domain.entity.homeComic.HomeComicDomain
import com.davidmerchan.domain.repository.HomeComicsRepository
import com.davidmerchan.network.ApiManager
import com.davidmerchan.network.model.GeneralResponse
import javax.inject.Inject

class HomeComicsRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager
) : HomeComicsRepository {
    override suspend fun getComicsHome(): Resource<HomeComicDomain> {
        val serializer = GeneralResponse.serializer(HomeComicsDataResponse.serializer())
        val response = apiManager.get(
            endpoint = "comics",
            serializer = serializer
        )
        return when {
            response.isSuccess -> {
                response.getOrNull()?.let {
                    val result: GeneralResponse<HomeComicsDataResponse> = it
                    Resource.Success(HomeComicDomain(result.status))
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
