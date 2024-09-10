package com.davidmerchan.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.davidmerchan.core.model.toMoney
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
            isCartEmpty -> ShoppingCartEmpty(onBackPressed = onBackPressed)
            cartItems.isNotEmpty() -> ShoppingCartContent(
                modifier = modifier,
                comics = cartItems,
                onBackPressed = onBackPressed,
                onRemoveComicFromCart = {
                    shoppingCartViewModel.handleEvent(ShoppingCartUiIntent.RemoveComicFromCart(it))
                },
                onMakePayment = {
                    shoppingCartViewModel.handleEvent(ShoppingCartUiIntent.ShowPaymentDialog(true))
                },
                onClearCart = {
                    shoppingCartViewModel.handleEvent(ShoppingCartUiIntent.ClearCart)
                }
            )

            error != null -> ErrorScreen(error = error)
        }
        if (isPaid) {
            CompletePaymentDialog(modifier) {
                shoppingCartViewModel.handleEvent(ShoppingCartUiIntent.ShowPaymentDialog(false))
            }
        }
    }
}

@Composable
fun CompletePaymentDialog(
    modifier: Modifier = Modifier,
    onCloseDialog: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_celebration),
                contentDescription = null
            )
        },
        title = {
            Text(text = "Gracias por tu compra")
        },
        text = {
            Text(text = stringResource(id = R.string.shopping_cart_buy_message))
        },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onCloseDialog()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.shopping_cart_complete_button),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartContent(
    modifier: Modifier = Modifier,
    comics: List<ShoppingCartItem>,
    onBackPressed: () -> Unit,
    onMakePayment: () -> Unit,
    onRemoveComicFromCart: (Long) -> Unit,
    onClearCart: () -> Unit
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
                },
                actions = {
                    IconButton(onClick = { onClearCart() }) {
                        Icon(Icons.Filled.Delete, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .weight(1F),
            ) {
                items(comics) { comic ->
                    ShoppingCartItem(
                        comic = comic,
                        onRemoveComicFromCart = onRemoveComicFromCart
                    )
                }
            }
            Button(
                onClick = onMakePayment,
                colors = ButtonDefaults.buttonColors(Color.Green),
                shape = RoundedCornerShape(40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.shopping_cart_buy_button),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ShoppingCartItem(
    modifier: Modifier = Modifier,
    comic: ShoppingCartItem,
    onRemoveComicFromCart: (Long) -> Unit
) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = comic.image,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .weight(1F)
            ) {
                Text(
                    text = comic.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row {
                    Text(
                        text = stringResource(id = R.string.shopping_cart_price_title),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500)
                    )
                    Text(text = comic.totalPrice.toMoney(), fontSize = 14.sp)
                }
            }
            IconButton(
                onClick = {
                    onRemoveComicFromCart(comic.comicId)
                }
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.background(Color.Red),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartEmpty(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
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
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.shopping_cart_empty_title))
        }
    }
}

@Preview
@Composable
private fun ShoppingCartItemPreview() {
    MaterialTheme {
        ShoppingCartItem(
            comicId = 100L,
            title = "IronMan",
            image = "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
            totalPrice = 100.0
        )
    }
}
