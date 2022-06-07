package com.martini.dartplayer.presentation.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.components.addToPlaylist.appBar.AddToPlaylistAppBar
import com.martini.dartplayer.presentation.components.addToPlaylist.content.AddToPlaylistContent
import com.martini.dartplayer.presentation.components.addToPlaylist.content.AddToPlaylistActionButton

@ExperimentalMaterialApi
@Composable
fun AddToPlaylistScreen(navController: NavController) {

    fun popBack() {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            AddToPlaylistAppBar(
                popBack = { popBack() }
            )
        },
        content = { AddToPlaylistContent() },
        floatingActionButton = { AddToPlaylistActionButton() }
    )
}