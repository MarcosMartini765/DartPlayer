package com.martini.dartplayer.presentation.components.artist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@ExperimentalMaterialApi
@Composable
fun ArtistContent(
    onMenuClick: () -> Unit
) {
    Column(
        Modifier.fillMaxSize()
    ) {
        ArtistBanner()
        Divider()
        ArtistAlbums(onMenuClick = onMenuClick)
    }
}