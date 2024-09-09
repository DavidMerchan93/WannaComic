package com.davidmerchan.wannacomic

import kotlinx.serialization.Serializable

object Screen {
    @Serializable
    object Home

    @Serializable
    data class ComicDetail(val id: Long)

    @Serializable
    object ShoppingCart

}
