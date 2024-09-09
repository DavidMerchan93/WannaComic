package com.davidmerchan.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.davidmerchan.domain.entity.homeComic.HomeComicsListDomain.HomeComic
import com.davidmerchan.home.presentation.intents.HomeUiIntent
import kotlin.random.Random

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onShowComicDetail: (Long) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.handleIntent(HomeUiIntent.LoadHomeComics)
    }

    Box(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        when {
            uiState.isLoading -> HomeScreenLoading()
            uiState.data != null -> HomeScreenContent(
                comics = uiState.data,
                onShowComicDetail = onShowComicDetail
            )

            uiState.error != null -> HomeScreenError(error = uiState.error)
        }
    }
}

@Composable
fun HomeScreenLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text(text = stringResource(id = R.string.home_load_comics))
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    comics: List<HomeComic>?,
    onShowComicDetail: (Long) -> Unit
) {
    if (comics.isNullOrEmpty().not()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 12.dp)
        ) {
            items(comics!!) { item ->
                HomeComicCard(item, onShowComicDetail)
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.home_empty_comics))
        }
    }
}

@Composable
fun HomeScreenError(modifier: Modifier = Modifier, error: String?) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = error ?: stringResource(id = R.string.home_error_comics))
    }
}

@Composable
fun HomeComicCard(comic: HomeComic, onShowComicDetail: (Long) -> Unit) {
    val colors = generateRandomGradientColors()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 4.dp)
            .clickable { onShowComicDetail(comic.id) },
        elevation = CardDefaults.cardElevation()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(colors)
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                println(comic.thumbnail)
                AsyncImage(
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(comic.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = comic.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight(600),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun generateRandomGradientColors(): List<Color> {
    val startColor = Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256)
    )
    val endColor = Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256)
    )
    return listOf(startColor, endColor)
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    MaterialTheme {
        HomeScreenContent(
            comics = emptyList(),
            onShowComicDetail = {}
        )
    }
}