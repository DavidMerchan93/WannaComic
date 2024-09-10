package com.davidmerchan.data.repository

import com.davidmerchan.core.model.IoDispatcher
import com.davidmerchan.core.model.Resource
import com.davidmerchan.data.models.homeComics.HomeComicsDataResponse
import com.davidmerchan.domain.model.HomeComicsListDomain
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

    /**
     * This function retrieves the list of comics for the home screen.
     *
     * @return A [Resource] object that encapsulates the result of the operation.
     * The result can be either a [Resource.Success] containing a [HomeComicsListDomain] object,
     * a [Resource.Error] containing an error message, or [Resource.Loading] indicating that the operation is in progress.
     *
     * @throws CancellationException If the coroutine is cancelled.
     * @throws IOException If an I/O error occurs while making the network request.
     * @throws HttpException If an HTTP error occurs while making the network request.
     * @throws JsonException If a JSON parsing error occurs while deserializing the response.
     */
    override suspend fun getComicsHome(): Resource<HomeComicsListDomain> {
        // Serialize the response using the GeneralResponse serializer and HomeComicsDataResponse serializer
        val serializer = GeneralResponse.serializer(HomeComicsDataResponse.serializer())

        // Make a network request to retrieve the comics home data using the provided endpoint and serializer
        val response = withContext(ioDispatcher) {
            apiManager.get(
                endpoint = ENDPOINT,
                serializer = serializer
            )
        }

        // Process the response and return the appropriate Resource object
        return when {
            response.isSuccess -> {
                // If the response is successful, deserialize the data and convert it to a HomeComicsListDomain object
                response.getOrNull()?.let {
                    Resource.Success(it.data.toDomain())
                } ?: run {
                    // If the deserialized data is null, return an error Resource
                    Resource.Error("General Error")
                }
            }

            response.isFailure -> {
                // If the response is a failure, return an error Resource with the error message
                Resource.Error(response.exceptionOrNull()?.message ?: "General Error")
            }

            else -> {
                // If the response is neither a success nor a failure, return a loading Resource
                Resource.Loading
            }
        }
    }

    companion object {
        private const val ENDPOINT = "comics"
    }
}
