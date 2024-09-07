package com.davidmerchan.network.di

import com.davidmerchan.network.ApiManager
import com.davidmerchan.network.KtorHttpClient
import com.davidmerchan.network.KtorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun getHttpClient(ktorHttpClient: KtorHttpClient): HttpClient =
        ktorHttpClient.getHttpClient()

    @Provides
    fun getApiManager(client: HttpClient): ApiManager = KtorManager(client)
}
