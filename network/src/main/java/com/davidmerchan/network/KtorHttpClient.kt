package com.davidmerchan.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * This object provides a single instance of a Ktor HttpClient configured with common networking settings.
 *
 * The HttpClient is configured with the following features:
 * - ContentNegotiation: Uses JSON for content negotiation with the specified Json configuration.
 * - Logging: Logs all HTTP requests and responses at the ALL log level.
 * - ResponseObserver: Logs the HTTP status of each response.
 * - DefaultRequest: Sets the Content-Type header to application/json for all requests.
 * - HttpTimeout: Sets the request, connect, and socket timeouts to 30 seconds.
 *
 * @return A configured HttpClient instance.
 */
object KtorHttpClient {

    private const val TIME_OUT = 30_000L
    private const val TAG = "KtorClient"
    private const val HTTP_STATUS = "http_status:"

    fun getHttpClient() = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    useAlternativeNames = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i(TAG, message)
                }
            }
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.i(HTTP_STATUS, "$response")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }
    }
}
