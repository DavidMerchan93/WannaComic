package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.repository.ShoppingCartDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteComicFromCartUseCaseTest {

    private val shoppingCartDataRepository: ShoppingCartDataRepository = mockk()

    private lateinit var deleteComicFromCartUseCase: DeleteComicFromCartUseCase

    @Before
    fun setUp() {
        deleteComicFromCartUseCase = DeleteComicFromCartUseCase(shoppingCartDataRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(shoppingCartDataRepository)
    }

    @Test
    fun `should delete comic from cart when repository returns true`() = runBlocking {
        // Given
        coEvery { shoppingCartDataRepository.removeComicFromCart(1L) } returns true

        // When
        val result = deleteComicFromCartUseCase(1L)

        // Then
        coVerify { shoppingCartDataRepository.removeComicFromCart(1L) }
        assertTrue(result)
    }

    @Test
    fun `should delete comic from cart when repository returns false`() = runBlocking {
        // Given
        coEvery { shoppingCartDataRepository.removeComicFromCart(1L) } returns false

        // When
        val result = deleteComicFromCartUseCase(1L)

        // Then
        coVerify { shoppingCartDataRepository.removeComicFromCart(1L) }
        assertFalse(result)
    }

}