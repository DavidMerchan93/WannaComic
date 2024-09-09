package com.davidmerchan.data.repository

import com.davidmerchan.core.model.IoDispatcher
import com.davidmerchan.database.dao.ShoppingCartDao
import com.davidmerchan.database.entity.CartComicEntity
import com.davidmerchan.domain.model.ComicDetailModel
import com.davidmerchan.domain.repository.LocalComicDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalComicDetailRepositoryImpl @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LocalComicDetailRepository {
    override suspend fun addToCart(comicDetailModel: ComicDetailModel) {
        withContext(ioDispatcher) {
            shoppingCartDao.add(
                entity = CartComicEntity(
                    cid = comicDetailModel.id,
                    title = comicDetailModel.title,
                    image = comicDetailModel.thumbnail,
                    price = comicDetailModel.price.toString()
                )
            )
        }
    }

    override suspend fun removeFromCart(comicId: Long) {
        withContext(ioDispatcher) {
            shoppingCartDao.removeById(comicId)
        }
    }

    override suspend fun getComicDetail(comicId: Long): ComicDetailModel? {
        val response = withContext(ioDispatcher) {
            shoppingCartDao.getById(comicId)
        }

        return response?.let {
            ComicDetailModel(
                id = it.cid,
                title = it.title,
                thumbnail = it.image,
                price = it.price.toDouble(),
                isAvailable = true,
                pages = 1,
                copyright = "",
                author = listOf(),
                description = ""
            )
        }
    }
}