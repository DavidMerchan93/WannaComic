package com.davidmerchan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidmerchan.database.dao.ShoppingCartDao
import com.davidmerchan.database.entity.CartComicEntity

@Database(entities = [CartComicEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartDao
}
