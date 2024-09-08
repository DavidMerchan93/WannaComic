package com.davidmerchan.di

import com.davidmerchan.core.model.IoDispatcher
import com.davidmerchan.data.repository.HomeComicsRepositoryImpl
import com.davidmerchan.domain.repository.HomeComicsRepository
import com.davidmerchan.network.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeComicsRepository(
        apiManager: ApiManager,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): HomeComicsRepository = HomeComicsRepositoryImpl(apiManager, ioDispatcher)

}
