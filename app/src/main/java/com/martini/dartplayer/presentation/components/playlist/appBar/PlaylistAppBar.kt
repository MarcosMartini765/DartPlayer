package com.martini.dartplayer.presentation.components.playlist.appBar

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.martini.dartplayer.presentation.components.playlist.appBar.dropDown.PlaylistAppBarDropDown

@Composable
fun PlaylistAppBar(
    popBack: () -> Unit
) {
    TopAppBar(
        title = { PlaylistAppBarTitle() },
        navigationIcon = {
            PlaylistAppBarNav(
                popBack = popBack
            )
        },
        actions = {
            PlaylistAppBarDeleteSelected()
            PlaylistAppBarDropDown(popBack = popBack)
        }
    )
}