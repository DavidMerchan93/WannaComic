package com.davidmerchan.database.di

import android.content.Context
import androidx.room.Room
import com.davidmerchan.database.AppDatabase
import com.davidmerchan.database.dao.ShoppingCartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "WannaDatabase"
    ).build()

    @Provides
    @Singleton
    fun provideShoppingCartDao(database: AppDatabase): ShoppingCartDao =
        database.shoppingCartDao()
}
