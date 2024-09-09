package com.davidmerchan.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ComicDetailScreen(
    modifier: Modifier = Modifier,
    comicId: Long
) {
    Text("Detalles del comic: $comicId")
}
