package com.davidmerchan.network

import com.davidmerchan.network.model.GeneralRequest

interface ApiManager {
    suspend fun <T> get(endpoint: String): Result<T>
    suspend fun <R, T> post(url: String, request: GeneralRequest<R>): Result<T>
}
