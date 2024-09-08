package com.davidmerchan.network.di

import com.davidmerchan.network.ApiManager
import com.davidmerchan.network.KtorHttpClient
import com.davidmerchan.network.KtorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getHttpClient(): HttpClient =
        KtorHttpClient.getHttpClient()

    @Provides
    @Singleton
    fun getApiManager(client: HttpClient): ApiManager = KtorManager(client)
}
