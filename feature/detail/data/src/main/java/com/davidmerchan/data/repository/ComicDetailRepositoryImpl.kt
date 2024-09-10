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

    /**
     * Retrieves the comic detail for the given [id].
     *
     * @param id The unique identifier of the comic.
     *
     * @return A [Resource] object containing the comic detail data or an error message.
     *
     * - If the request is successful, the [Resource] will be [Resource.Success] with the comic detail data.
     * - If the request fails due to an exception, the [Resource] will be [Resource.Error] with the error message.
     * - If the request is still in progress, the [Resource] will be [Resource.Loading].
     */
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