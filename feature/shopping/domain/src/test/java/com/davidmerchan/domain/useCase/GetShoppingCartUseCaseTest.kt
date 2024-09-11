package com.davidmerchan.domain.useCase

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.ShoppingCartItem
import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetShoppingCartUseCaseTest {

    private val shoppingCartDataRepository: ShoppingCartDataRepository = mockk()

    private lateinit var getShoppingCartUseCase: GetShoppingCartUseCase

    @Before
    fun setUp() {
        getShoppingCartUseCase = GetShoppingCartUseCase(shoppingCartDataRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(shoppingCartDataRepository)
    }

    @Test
    fun `should get shopping cart items when repository returns Loading`() = runBlocking {
        val response = Resource.Loading

        // Given
        coEvery { shoppingCartDataRepository.getShoppingCart() } returns response

        // When
        val result = getShoppingCartUseCase()

        // Then
        coVerify { shoppingCartDataRepository.getShoppingCart() }
        assertEquals(result, response)
        assertTrue(result is Resource.Loading)
    }

    @Test
    fun `should get shopping cart items when repository returns Success`() = runBlocking {
        val shoppingCartItemMock: ShoppingCartItem = mockk()
        val response = Resource.Success(
            listOf(
                shoppingCartItemMock
            )
        )

        // Given
        coEvery { shoppingCartDataRepository.getShoppingCart() } returns response

        // When
        val result = getShoppingCartUseCase()

        // Then
        coVerify { shoppingCartDataRepository.getShoppingCart() }
        assertEquals(result, response)
        assertTrue(result is Resource.Success)

        confirmVerified(shoppingCartItemMock)
    }

    @Test
    fun `should get shopping cart items when repository returns Error`() = runBlocking {
        val response = Resource.Error("General Error")

        // Given
        coEvery { shoppingCartDataRepository.getShoppingCart() } returns response

        // When
        val result = getShoppingCartUseCase()

        // Then
        coVerify { shoppingCartDataRepository.getShoppingCart() }
        assertEquals(result, response)
        assertTrue(result is Resource.Error)
    }

}