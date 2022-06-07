package com.martini.dartplayer.presentation.components.addToPlaylist.appBar

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun AddToPlaylistAppBar(
    popBack: () -> Unit
) {

    TopAppBar(
        title = { AddToPlaylistAppBarTitle() },
        navigationIcon = { AddToPlaylistAppBarNavIcon(popBack = popBack) }
    )
}