package com.davidmerchan.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidmerchan.core.model.ui.ErrorScreen
import com.davidmerchan.core.model.ui.LoaderScreen
import com.davidmerchan.presentation.ComicDetailViewModel
import com.davidmerchan.presentation.intent.ComicDetailUiIntent

@Composable
fun ComicDetailScreen(
    comicId: Long,
    modifier: Modifier = Modifier,
    comicDetailViewModel: ComicDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onGoToCart: () -> Unit
) {
    val state by comicDetailViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        comicDetailViewModel.handleEvent(ComicDetailUiIntent.LoadComicDetail(comicId))
    }

    with(state) {
        when {
            isLoading -> LoaderScreen()
            error != null -> ErrorScreen(error = state.error!!)
            comicData != null -> ComicDetailContent(
                modifier = modifier,
                comicDetail = state.comicData!!,
                onBackPressed = onBackPressed,
                onGoToCart = onGoToCart,
                onAddToCart = {
                    state.comicData?.let {
                        comicDetailViewModel.handleEvent(ComicDetailUiIntent.AddToCart(it))
                    }
                },
                onRemoveFromCart = {
                    state.comicData?.let {
                        comicDetailViewModel.handleEvent(ComicDetailUiIntent.RemoveFromCart(it.id))
                    }
                }
            )

            else -> LoaderScreen()
        }
    }
}
