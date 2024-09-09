package com.davidmerchan.presentation

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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.davidmerchan.core.model.toMoney
import com.davidmerchan.domain.model.ComicDetailModel
import com.davidmerchan.presentation.intent.ComicDetailUiIntent

@Composable
fun ComicDetailScreen(
    comicId: Long,
    modifier: Modifier = Modifier,
    comicDetailViewModel: ComicDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onGoToCart: () -> Unit
) {
    val state = comicDetailViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        comicDetailViewModel.handleEvent(ComicDetailUiIntent.LoadComicDetail(comicId))
    }

    when {
        state.value.isLoading -> ComicDetailLoading()
        state.value.error != null -> ComicDetailError(error = state.value.error!!)
        state.value.comicData != null -> ComicDetailContent(
            modifier = modifier,
            comicDetail = state.value.comicData!!,
            onBackPressed = onBackPressed,
            onGoToCart = onGoToCart,
            onAddToCart = {
                state.value.comicData?.let {
                    comicDetailViewModel.handleEvent(ComicDetailUiIntent.AddToCart(it))
                }
            },
            onRemoveFromCart = {
                state.value.comicData?.let {
                    comicDetailViewModel.handleEvent(ComicDetailUiIntent.RemoveFromCart(it.id))
                }
            }
        )

        else -> ComicDetailLoading()
    }
}

@Composable
fun ComicDetailLoading() {
    Text("Loading...")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicDetailContent(
    modifier: Modifier = Modifier,
    comicDetail: ComicDetailModel,
    onBackPressed: () -> Unit,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit,
    onGoToCart: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = comicDetail.id.toString()) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onGoToCart) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Add to Cart")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = comicDetail.thumbnail,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = comicDetail.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = comicDetail.copyright,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.comic_detail_price_title),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Text(
                            text = comicDetail.price.toMoney(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.comic_detail_pages_title),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Text(
                            text = comicDetail.pages.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = stringResource(id = R.string.comic_detail_editors_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(comicDetail.author) {
                        Text(
                            text = it,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Synopsis section
                Text(
                    text = stringResource(id = R.string.comic_detail_synopsis_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = comicDetail.description.ifEmpty { "N/A" },
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (comicDetail.hasInCart) {
                            onRemoveFromCart()
                        } else {
                            onAddToCart()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp)
                ) {
                    Text(
                        text = if (comicDetail.hasInCart) {
                            stringResource(id = R.string.comic_detail_delete_button)
                        } else {
                            stringResource(id = R.string.comic_detail_buy_button)
                        },
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ComicDetailError(error: String) {
    Text(text = error)
}

@Preview
@Composable
internal fun ComicDetailContentPreview() {
    MaterialTheme {
        ComicDetailContent(
            comicDetail = ComicDetailModel(
                id = 1,
                title = "Amazing Spider-Man #40",
                thumbnail = "https://example.com/thumbnail.jpg",
                copyright = "Marvel",
                description = "This is a sample comic description.",
                price = 19.99,
                isAvailable = true,
                pages = 120,
                author = emptyList()
            ),
            onBackPressed = {},
            onAddToCart = {},
            onGoToCart = {},
            onRemoveFromCart = {}
        )
    }
}
