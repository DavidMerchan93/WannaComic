package com.davidmerchan.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GeneralResponse<out T>(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val data: T
)

@Serializable
data class GeneralRequest<out T>(val data: T)
