package com.davidmerchan.di

import com.davidmerchan.core.model.IoDispatcher
import com.davidmerchan.data.repository.ShoppingCartDataRepositoryImpl
import com.davidmerchan.database.dao.ShoppingCartDao
import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShoppingCartModule {

    @Provides
    @Singleton
    fun provideShoppingCartRepository(
        shoppingCartDao: ShoppingCartDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ShoppingCartDataRepository = ShoppingCartDataRepositoryImpl(shoppingCartDao, ioDispatcher)
}