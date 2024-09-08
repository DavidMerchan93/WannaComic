package com.davidmerchan.data.repository

import com.davidmerchan.core.model.IoDispatcher
import com.davidmerchan.core.model.Resource
import com.davidmerchan.data.models.homeComics.HomeComicsDataResponse
import com.davidmerchan.domain.entity.homeComic.HomeComicsListDomain
import com.davidmerchan.domain.repository.HomeComicsRepository
import com.davidmerchan.network.ApiManager
import com.davidmerchan.network.model.GeneralResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeComicsRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : HomeComicsRepository {
    override suspend fun getComicsHome(): Resource<HomeComicsListDomain> {
        val serializer = GeneralResponse.serializer(HomeComicsDataResponse.serializer())
        val response = withContext(ioDispatcher) {
            apiManager.get(
                endpoint = ENDPOINT,
                serializer = serializer
            )
        }
        return when {
            response.isSuccess -> {
                response.getOrNull()?.let {
                    Resource.Success(it.data.toDomain())
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

    companion object {
        private const val ENDPOINT = "comics"
    }
}
