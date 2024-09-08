package com.davidmerchan.core.model

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoreModule  {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
