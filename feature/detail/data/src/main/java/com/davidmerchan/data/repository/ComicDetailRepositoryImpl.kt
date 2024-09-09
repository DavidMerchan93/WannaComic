package com.davidmerchan.data.repository

import com.davidmerchan.core.model.IoDispatcher
import com.davidmerchan.core.model.Resource
import com.davidmerchan.data.model.ComicDetailResponse
import com.davidmerchan.domain.model.ComicDetailModel
import com.davidmerchan.domain.repository.ComicDetailRepository
import com.davidmerchan.network.ApiManager
import com.davidmerchan.network.model.GeneralResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicDetailRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ComicDetailRepository {
    override suspend fun getComicDetail(id: Long): Resource<ComicDetailModel?> {
        val serializer = GeneralResponse.serializer(ComicDetailResponse.serializer())
        val response = withContext(ioDispatcher) {
            apiManager.get(
                endpoint = "${ENDPOINT}/${id}",
                serializer = serializer
            )
        }
        return when {
            response.isSuccess -> {
                response.getOrNull()?.let {
                    Resource.Success(it.data.toDomain(it.copyright))
                }?: run {
                    Resource.Error("General Error")
                }
            }

            response.isFailure -> {
                Resource.Error(response.exceptionOrNull()?.message?: "General Error")
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