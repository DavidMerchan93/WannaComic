package com.davidmerchan.network

import android.util.Log
import com.davidmerchan.network.model.GeneralRequest
import com.davidmerchan.network.model.GeneralResponse
import com.davidmerchan.network.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path
import javax.inject.Inject

class KtorManager @Inject constructor(
    private val httpClient: HttpClient
): ApiManager {

    override suspend fun <T> get(endpoint: String): Resource<T> {
        return try {
            val response = httpClient.get {
                url {
                    host = BASE_URL
                    path(endpoint)
                    parameters.append("apikey", TOKEN)
                }
            }
            val body: GeneralResponse<T> = response.body()
            Resource.Success(body.data)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
            Resource.Error(e.message ?: "$TAG : General Error")
        }
    }

    override suspend fun <R, T> post(url: String, request: GeneralRequest<R>): Resource<T> {
        return try {
            val response = httpClient.post(url) {
                setBody(request)
            }
            val body: GeneralResponse<T> = response.body()
            Resource.Success(body.data)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
            Resource.Error(e.message ?: "$TAG : General Error")
        }
    }

    companion object {
        private const val TOKEN = "9a2cb734312c25350fa41f6b91d74768"
        private const val BASE_URL = "https://gateway.marvel.com:443/v1/public"
        private const val TAG = "KtorManager"
    }

}
