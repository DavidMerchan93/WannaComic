package com.davidmerchan.domain

import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import com.davidmerchan.domain.useCase.ClearShoppingCartUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ClearShoppingCartUseCaseTest {
    private val shoppingCartDataRepository: ShoppingCartDataRepository = mockk()

    private lateinit var clearShoppingCartUseCase: ClearShoppingCartUseCase

    @Before
    fun setUp() {
        clearShoppingCartUseCase = ClearShoppingCartUseCase(shoppingCartDataRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(shoppingCartDataRepository)
    }

    @Test
    fun `should clear shopping cart when repository returns true`() = runBlocking {
        // Given
        coEvery { shoppingCartDataRepository.clearShoppingCart() } returns true

        // When
        val result = clearShoppingCartUseCase()

        // Then
        coVerify { shoppingCartDataRepository.clearShoppingCart() }
        assertTrue(result)
    }
}
