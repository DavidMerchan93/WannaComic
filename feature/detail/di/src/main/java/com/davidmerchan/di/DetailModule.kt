package com.davidmerchan.di

import com.davidmerchan.core.model.IoDispatcher
import com.davidmerchan.data.repository.ComicDetailRepositoryImpl
import com.davidmerchan.domain.repository.ComicDetailRepository
import com.davidmerchan.network.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DetailModule {

    @Provides
    @Singleton
    fun provideComicDetailRepository(
        apiManager: ApiManager,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ComicDetailRepository = ComicDetailRepositoryImpl(apiManager, ioDispatcher)
}