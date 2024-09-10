package com.davidmerchan.network

import android.util.Log
import com.davidmerchan.network.model.GeneralRequest
import com.davidmerchan.network.model.GeneralResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Date
import javax.inject.Inject

class KtorManager @Inject constructor(
    private val httpClient: HttpClient
): ApiManager {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Performs a GET request to the Marvel API with the provided endpoint and deserializes the response into the specified type.
     *
     * @param endpoint The API endpoint to be called.
     * @param serializer The serializer to be used for deserializing the response.
     * @return A [Result] object containing the deserialized response if the request is successful, or an exception if it fails.
     * @throws Exception If an error occurs during the request or deserialization process.
     */
    override suspend fun <T> get(
        endpoint: String,
        serializer: KSerializer<GeneralResponse<T>>
    ): Result<GeneralResponse<T>> {
        return try {
            val ts = Date().time
            val response = httpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    encodedPath = "$ENCODE_URL$endpoint"
                    parameters.append("apikey", PUBLIC_KEY)
                    parameters.append("ts",ts.toString())
                    parameters.append("hash",getHash(ts))
                }
            }
            val bodyText = response.bodyAsText()
            val result = json.decodeFromString(serializer, bodyText)
            Result.success(result)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Performs a POST request to the specified URL with the provided request object and deserializes the response into the specified type.
     *
     * @param url The URL to be called.
     * @param request The request object to be sent in the POST request.
     * @return A [Result] object containing the status of the response if the request is successful, or an exception if it fails.
     * @throws Exception If an error occurs during the request or deserialization process.
     */
    override suspend fun <R, T> post(url: String, request: GeneralRequest<R>): Result<String> {
        return try {
            val response = httpClient.post(url) {
                setBody(request)
            }
            val body: GeneralResponse<T> = response.body()
            Result.success(body.status)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Generates a hash using the MD5 algorithm for the Marvel API authentication.
     *
     * @param ts The timestamp used in the authentication process.
     * @return A string representing the generated hash.
     * @throws NoSuchAlgorithmException If the MD5 algorithm is not available.
     */
    private fun getHash(ts: Long): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest((ts.toString() + PRIVATE_KEY + PUBLIC_KEY).toByteArray())
        val bigInt = BigInteger(1, digest)
        return bigInt.toString(16).padStart(32, '0')
    }

    companion object {
        private const val TAG = "KtorManager"
        private const val BASE_URL = BuildConfig.MARVEL_URL
        private const val ENCODE_URL = BuildConfig.MARVEL_ENCODE_URL
        private const val PRIVATE_KEY = BuildConfig.MARVEL_PRIVATE_KEY
        private const val PUBLIC_KEY = BuildConfig.MARVEL_PUBLIC_KEY
    }
}
