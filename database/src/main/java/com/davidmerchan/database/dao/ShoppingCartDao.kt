package com.davidmerchan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.davidmerchan.database.entity.CartComicEntity

@Dao
interface ShoppingCartDao {

    @Query("SELECT * FROM CartComicEntity WHERE cid = :id")
    fun getById(id: Long): CartComicEntity?

    @Query("SELECT * FROM CartComicEntity")
    fun getAll(): List<CartComicEntity>

    @Insert
    fun add(entity: CartComicEntity)

    @Query("DELETE FROM CartComicEntity WHERE cid=:comicId")
    fun removeById(comicId: Long)
}
