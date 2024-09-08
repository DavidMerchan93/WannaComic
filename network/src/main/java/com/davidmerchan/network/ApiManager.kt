package com.davidmerchan.network

import com.davidmerchan.network.model.GeneralRequest
import com.davidmerchan.network.model.GeneralResponse
import kotlinx.serialization.KSerializer

interface ApiManager {
    suspend fun <R, T> post(url: String, request: GeneralRequest<R>): Result<String>
    suspend fun <T> get(
        endpoint: String,
        serializer: KSerializer<GeneralResponse<T>>
    ): Result<GeneralResponse<T>>
}
