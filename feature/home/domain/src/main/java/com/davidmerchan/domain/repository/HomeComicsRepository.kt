package com.davidmerchan.domain.repository

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.entity.homeComic.HomeComicDomain

interface HomeComicsRepository {
    suspend fun getComicsHome(): Resource<HomeComicDomain>
}
