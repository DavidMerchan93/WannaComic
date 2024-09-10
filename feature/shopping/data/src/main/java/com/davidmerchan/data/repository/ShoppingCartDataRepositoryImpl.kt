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

    /**
     * Retrieves the current state of the shopping cart from the database.
     *
     * @return A [Resource] object containing either a [List] of [ShoppingCartItem] objects or an error message.
     *
     * This function uses coroutines to perform the database operation on the IO dispatcher.
     * If the operation is successful, it maps the response to a list of [ShoppingCartItem] objects and wraps it in a [Resource.Success].
     * If an exception occurs during the operation, it wraps the error message in a [Resource.Error].
     */
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

    /**
     * Clears the shopping cart by removing all items from the database.
     *
     * @return A boolean indicating whether the operation was successful.
     *
     * This function uses coroutines to perform the database operation on the IO dispatcher.
     * If the operation is successful, it returns `true`.
     * If an exception occurs during the operation, it returns `false`.
     *
     * @throws Exception If an error occurs during the database operation.
     */
    override suspend fun clearShoppingCart(): Boolean {
        return try {
            withContext(ioDispatcher) {
                shoppingCartDao.clearCart()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Removes a comic from the shopping cart based on the provided comic ID.
     *
     * @param comicId The unique identifier of the comic to be removed.
     *
     * @return A boolean indicating whether the operation was successful.
     * If the operation is successful, it returns `true`.
     * If an exception occurs during the operation, it returns `false`.
     *
     * This function uses coroutines to perform the database operation on the IO dispatcher.
     * It attempts to remove the comic with the given [comicId] from the shopping cart.
     * If the operation is successful, it returns `true`.
     * If an exception occurs during the operation, it catches the exception and returns `false`.
     *
     * @throws Exception If an error occurs during the database operation.
     */
    override suspend fun removeComicFromCart(comicId: Long): Boolean {
        return try {
            withContext(ioDispatcher) {
                shoppingCartDao.removeById(comicId)
            }
            true
        } catch (e: Exception) {
            false
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