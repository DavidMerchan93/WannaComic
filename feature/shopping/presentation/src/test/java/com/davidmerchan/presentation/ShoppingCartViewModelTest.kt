package com.davidmerchan.presentation

import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.model.ShoppingCartItem
import com.davidmerchan.domain.useCase.ClearShoppingCartUseCase
import com.davidmerchan.domain.useCase.DeleteComicFromCartUseCase
import com.davidmerchan.domain.useCase.GetShoppingCartUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ShoppingCartViewModelTest {

    private val getShoppingCartUseCase: GetShoppingCartUseCase = mockk()
    private val deleteComicFromCartUseCase: DeleteComicFromCartUseCase = mockk()
    private val clearShoppingCartUseCase: ClearShoppingCartUseCase = mockk()

    private lateinit var shoppingCartViewModel: ShoppingCartViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        shoppingCartViewModel = ShoppingCartViewModel(
            getShoppingCartUseCase = getShoppingCartUseCase,
            deleteComicFromCartUseCase = deleteComicFromCartUseCase,
            clearShoppingCartUseCase = clearShoppingCartUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        confirmVerified(
            getShoppingCartUseCase,
            deleteComicFromCartUseCase,
            clearShoppingCartUseCase
        )
        Dispatchers.resetMain()
    }

    @Test
    fun `should return Loading when getShoppingCart is started loader`() = runBlocking {
        // Given
        val event = ShoppingCartUiIntent.LoadShoppingCart
        coEvery {
            getShoppingCartUseCase()
        } returns Resource.Loading

        // When
        shoppingCartViewModel.handleEvent(event)

        // Then
        assertEquals(
            ShoppingCartUiState(isLoading = true),
            shoppingCartViewModel.uiState.value
        )
        coVerify {
            getShoppingCartUseCase()
        }
    }

    @Test
    fun `should return Success when getShoppingCart is result filled`() = runBlocking {
        // Given
        val event = ShoppingCartUiIntent.LoadShoppingCart
        val shoppingCartItemMock: ShoppingCartItem = mockk()

        coEvery {
            getShoppingCartUseCase()
        } returns Resource.Success(listOf(shoppingCartItemMock))

        // When
        shoppingCartViewModel.handleEvent(event)

        // Then
        coVerify {
            getShoppingCartUseCase()
        }

        confirmVerified(shoppingCartItemMock)
        assertEquals(
            ShoppingCartUiState(cartItems = listOf(shoppingCartItemMock)),
            shoppingCartViewModel.uiState.value
        )
    }

    @Test
    fun `should return Success when getShoppingCart is result empty`() = runBlocking {
        // Given
        val event = ShoppingCartUiIntent.LoadShoppingCart

        coEvery {
            getShoppingCartUseCase()
        } returns Resource.Success(listOf())

        // When
        shoppingCartViewModel.handleEvent(event)

        // Then
        assertEquals(
            ShoppingCartUiState(isCartEmpty = true),
            shoppingCartViewModel.uiState.value
        )
        coVerify {
            getShoppingCartUseCase()
        }
    }

    @Test
    fun `should return Error when getShoppingCart is result wrong`() = runBlocking {
        // Given
        val event = ShoppingCartUiIntent.LoadShoppingCart

        coEvery {
            getShoppingCartUseCase()
        } returns Resource.Error("General error")

        // When
        shoppingCartViewModel.handleEvent(event)

        // Then
        assertEquals(
            ShoppingCartUiState(error = "General error"),
            shoppingCartViewModel.uiState.value
        )
        coVerify {
            getShoppingCartUseCase()
        }
    }

    @Test
    fun `should return Success when clearShoppingCart is result Success`() = runBlocking {
        // Given
        val event = ShoppingCartUiIntent.ClearCart

        coEvery {
            clearShoppingCartUseCase()
        } returns true

        // When
        shoppingCartViewModel.handleEvent(event)

        // Then
        assertEquals(
            ShoppingCartUiState(isLoading = true),
            shoppingCartViewModel.uiState.value
        )
        coVerify {
            clearShoppingCartUseCase()
            getShoppingCartUseCase()
        }
    }

    @Test
    fun `should return Success when removeComicFromCart is result Success`() = runBlocking {
        // Given
        val event = ShoppingCartUiIntent.RemoveComicFromCart(1L)

        coEvery {
            deleteComicFromCartUseCase(1L)
        } returns true

        // When
        shoppingCartViewModel.handleEvent(event)

        // Then
        assertEquals(
            ShoppingCartUiState(isLoading = true),
            shoppingCartViewModel.uiState.value
        )
        coVerify {
            deleteComicFromCartUseCase(1L)
            getShoppingCartUseCase()
        }
    }

    @Test
    fun `should return Success when ShowPaymentDialog is true`() = runBlocking {
        // Given
        val event = ShoppingCartUiIntent.ShowPaymentDialog(true)

        coEvery {
            clearShoppingCartUseCase()
        } returns true

        // When
        shoppingCartViewModel.handleEvent(event)

        // Then
        assertEquals(
            ShoppingCartUiState(isPaid = true),
            shoppingCartViewModel.uiState.value
        )
        coVerify {
            clearShoppingCartUseCase()
        }
    }

    @Test
    fun `should return Success when ShowPaymentDialog is false`() = runBlocking {
        // Given
        val event = ShoppingCartUiIntent.ShowPaymentDialog(false)

        coEvery {
            getShoppingCartUseCase()
        } returns Resource.Success(listOf())

        // When
        shoppingCartViewModel.handleEvent(event)

        // Then
        assertEquals(
            ShoppingCartUiState(isCartEmpty = true),
            shoppingCartViewModel.uiState.value
        )
        coVerify {
            getShoppingCartUseCase()
        }
    }

}