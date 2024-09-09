package com.davidmerchan.data.repository

import com.davidmerchan.core.model.IoDispatcher
import com.davidmerchan.core.model.Resource
import com.davidmerchan.database.dao.ShoppingCartDao
import com.davidmerchan.database.entity.CartComicEntity
import com.davidmerchan.domain.model.ShoppingCartItem
import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingCartDataRepositoryImpl @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ShoppingCartDataRepository {
    override suspend fun getShoppingCart(): Resource<List<ShoppingCartItem>> {
        return try {
            val response = withContext(ioDispatcher) {
                shoppingCartDao.getAll()
            }
            Resource.Success(response.mapShoppingCartResponse())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "General Error")
        }
    }

    private fun List<CartComicEntity>.mapShoppingCartResponse(): List<ShoppingCartItem> {
        return this.map {
            ShoppingCartItem(
                comicId = it.cid,
                title = it.title,
                image = it.image,
                totalPrice = it.price.toDouble()
            )
        }
    }
}