package com.davidmerchan.home.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidmerchan.core.model.ui.ErrorScreen
import com.davidmerchan.core.model.ui.LoaderScreen
import com.davidmerchan.home.presentation.HomeViewModel
import com.davidmerchan.home.presentation.R
import com.davidmerchan.home.presentation.intents.HomeUiIntent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onShowComicDetail: (Long) -> Unit,
    onGoToCart: () -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.handleIntent(HomeUiIntent.LoadHomeComics)
    }

    when {
        uiState.isLoading -> LoaderScreen(message = stringResource(id = R.string.home_load_comics))
        uiState.data != null -> HomeScreenContent(
            modifier = modifier,
            comics = uiState.data,
            onShowComicDetail = onShowComicDetail,
            onGoToCart = onGoToCart
        )

        uiState.error != null -> ErrorScreen(error = uiState.error!!)
    }
}
