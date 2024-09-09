package com.davidmerchan.core.model

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()
    data class Success<out T>(val result: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}
