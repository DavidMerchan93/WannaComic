package com.davidmerchan.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class GeneralResponse<out T>(
    @SerialName("code")
    val code: Int,
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: T
)

@Serializable
data class GeneralRequest<out T>(val data: T)
