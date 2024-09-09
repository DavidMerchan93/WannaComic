package com.davidmerchan.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartComicEntity(
    @PrimaryKey val cid: Long,
    val title: String,
    val price: String,
    val image: String
)
