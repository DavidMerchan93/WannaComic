package com.davidmerchan.network.model

import kotlinx.serialization.Serializable

sealed class Resource<out T> {
    data class Success<out T>(val result: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

@Serializable
data class GeneralResponse<out T>(val data: T)

@Serializable
data class GeneralRequest<out T>(val data: T)
