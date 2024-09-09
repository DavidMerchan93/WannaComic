package com.davidmerchan.domain.repository

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.ComicDetailModel

interface ComicDetailRepository {
    suspend fun getComicDetail(id: Long): Resource<ComicDetailModel?>
}
