package com.martini.dartplayer.presentation.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.components.createPlaylist.CreatePlaylistAppBar

@Composable
fun CreatePlaylistScreen(navController: NavController) {

    fun popBack() {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            CreatePlaylistAppBar(
                popBack = { popBack() }
            )
        },
        content = {}
    )
}