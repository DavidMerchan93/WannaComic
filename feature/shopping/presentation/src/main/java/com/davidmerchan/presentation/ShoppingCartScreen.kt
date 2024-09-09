package com.davidmerchan.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    shoppingCartViewModel: ShoppingCartViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        shoppingCartViewModel.handleEvent(ShoppingCartUiIntent.LoadShoppingCart)
    }
    Text("Shopping Cart")
}
