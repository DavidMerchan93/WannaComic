package com.davidmerchan.data.repository

import com.davidmerchan.core.model.Resource
import com.davidmerchan.database.dao.ShoppingCartDao
import com.davidmerchan.database.entity.CartComicEntity
import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingCartDataRepositoryImplTest {

    private val shoppingCartDao = mockk<ShoppingCartDao>()
    private val ioDispatcher = UnconfinedTestDispatcher()

    private lateinit var shoppingCartDataRepositoryImpl: ShoppingCartDataRepository

    @Before
    fun setUp() {
        shoppingCartDataRepositoryImpl = ShoppingCartDataRepositoryImpl(
            shoppingCartDao = shoppingCartDao,
            ioDispatcher = ioDispatcher
        )
    }

    @Test
    fun `should return shopping cart when dao returns data successfully`() = runTest {
        // Given
        val mockShoppingCart: CartComicEntity = mockk {
            every { cid } returns 1L
            every { image } returns "image.jpg"
            every { title } returns "Test Title"
            every { price } returns "10.0"
        }

        coEvery { shoppingCartDao.getAll() } returns listOf(mockShoppingCart)

        // When
        val result = shoppingCartDataRepositoryImpl.getShoppingCart()

        // Then
        assertTrue(result is Resource.Success)
        coVerify { shoppingCartDao.getAll() }
        verify {
            mockShoppingCart.cid
            mockShoppingCart.image
            mockShoppingCart.title
            mockShoppingCart.price
        }
    }

    @Test
    fun `should return error when dao throws exception`() = runTest {
        // Given
        coEvery { shoppingCartDao.getAll() } throws Exception("Test Error")

        // When
        val result = shoppingCartDataRepositoryImpl.getShoppingCart()

        // Then
        coVerify { shoppingCartDao.getAll() }
        assertEquals(Resource.Error("Test Error"), result)
    }

    @Test
    fun `should return true when comic is successfully removed from cart`() = runTest {
        // Given
        val comicId = 123L

        coEvery { shoppingCartDao.removeById(comicId) } just runs

        // When
        val result = shoppingCartDataRepositoryImpl.removeComicFromCart(comicId)

        // Then
        coVerify { shoppingCartDao.removeById(comicId) }
        assertTrue(result)
    }

    @Test
    fun `should return false when exception is thrown during comic removal`() = runTest {
        // Given
        val comicId = 123L

        coEvery { shoppingCartDao.removeById(comicId) } throws Exception("Test Error")

        // When
        val result = shoppingCartDataRepositoryImpl.removeComicFromCart(comicId)

        // Then
        coVerify { shoppingCartDao.removeById(comicId) }
        assertFalse(result)
    }

    @Test
    fun `should return true when cart is successfully cleared`() = runTest {
        // Given
        coEvery { shoppingCartDao.clearCart() } just runs

        // When
        val result = shoppingCartDataRepositoryImpl.clearShoppingCart()

        // Then
        coVerify { shoppingCartDao.clearCart() }
        assertTrue(result)
    }

    @Test
    fun `should return false when exception is thrown during cart clearing`() = runTest {
        // Given
        coEvery { shoppingCartDao.clearCart() } throws Exception("Test Error")

        // When
        val result = shoppingCartDataRepositoryImpl.clearShoppingCart()

        // Then
        coVerify { shoppingCartDao.clearCart() }
        assertFalse(result)
    }
}