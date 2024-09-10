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

    /**
     * Adds a comic detail model to the shopping cart.
     *
     * @param comicDetailModel The comic detail model to be added to the shopping cart.
     * This model contains information such as id, title, thumbnail, price, etc.
     * The function uses the [ioDispatcher] to perform the database operation in a non-blocking manner.
     * It maps the [comicDetailModel] to a [CartComicEntity] and then adds it to the shopping cart using the [shoppingCartDao].
     * @return Nothing.
     */
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

    /**
     * Removes a comic from the shopping cart based on its unique identifier.
     *
     * @param comicId The unique identifier of the comic to be removed from the shopping cart.
     * This function uses the [ioDispatcher] to perform the database operation in a non-blocking manner.
     * It calls the [shoppingCartDao.removeById] method to remove the comic with the given [comicId] from the shopping cart.
     * @return Nothing.
     */
    override suspend fun removeFromCart(comicId: Long) {
        withContext(ioDispatcher) {
            shoppingCartDao.removeById(comicId)
        }
    }

    /**
     * Retrieves the comic detail model from the shopping cart based on its unique identifier.
     *
     * @param comicId The unique identifier of the comic to retrieve from the shopping cart.
     * This function uses the [ioDispatcher] to perform the database operation in a non-blocking manner.
     * It calls the [shoppingCartDao.getById] method to retrieve the comic with the given [comicId] from the shopping cart.
     *
     * @return A [ComicDetailModel] object containing the comic's details if found in the shopping cart, or `null` if not found.
     * The returned [ComicDetailModel] object contains information such as id, title, thumbnail, price, availability, pages, copyright, author, and description.
     * In this implementation, the comic is assumed to be available, have 1 page, no copyright information, no author, and an empty description.
     */
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