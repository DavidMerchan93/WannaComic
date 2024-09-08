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

    val json = Json { ignoreUnknownKeys = true }

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
                    encodedPath = "/v1/public/$endpoint"
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

    private fun getHash(ts: Long): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest((ts.toString() + PRIVATE_KEY + PUBLIC_KEY).toByteArray())
        val bigInt = BigInteger(1, digest)
        return bigInt.toString(16).padStart(32, '0')
    }

    companion object {
        private const val BASE_URL = "gateway.marvel.com:443"
        private const val TAG = "KtorManager"
        private const val PRIVATE_KEY = "3c3a0ca2724af3cb37cb73d3d8f1802a81727348"
        private const val PUBLIC_KEY = "9a2cb734312c25350fa41f6b91d74768"
    }
}
