package com.davidmerchan.network

import com.davidmerchan.network.model.GeneralRequest
import com.davidmerchan.network.model.Resource

interface ApiManager {
    suspend fun <T> get(endpoint: String): Resource<T>
    suspend fun <R, T> post(url: String, request: GeneralRequest<R>): Resource<T>
}
