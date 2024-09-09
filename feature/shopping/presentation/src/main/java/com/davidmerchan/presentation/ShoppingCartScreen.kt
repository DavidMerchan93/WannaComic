package com.davidmerchan.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.davidmerchan.core.model.ui.ErrorScreen
import com.davidmerchan.core.model.ui.LoaderScreen
import com.davidmerchan.domain.model.ShoppingCartItem

@Composable
fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    shoppingCartViewModel: ShoppingCartViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val state by shoppingCartViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        shoppingCartViewModel.handleEvent(ShoppingCartUiIntent.LoadShoppingCart)
    }

    with(state) {
        when {
            isLoading -> LoaderScreen()
            isCartEmpty -> ShoppingCartEmpty()
            cartItems.isNotEmpty() -> ShoppingCartContent(modifier, cartItems, onBackPressed)
            error != null -> ErrorScreen(error = error)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartContent(
    modifier: Modifier = Modifier,
    comics: List<ShoppingCartItem>,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Carrito") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            items(comics) { comic ->
                Card {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = comic.image,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Text(text = comic.title, fontSize = 18.sp)
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(text = "$ ${comic.totalPrice} USD", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingCartEmpty(modifier: Modifier = Modifier) {
    Text(text = "Carrito vacio")
}
