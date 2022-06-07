package com.martini.dartplayer.presentation.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.components.toDelete.ToDeleteAppBar
import com.martini.dartplayer.presentation.components.toDelete.ToDeleteContent
import com.martini.dartplayer.presentation.components.toDelete.ToDeleteFab

@ExperimentalMaterialApi
@Composable
fun ToDeleteScreen(navController: NavController) {
    Scaffold(
        topBar = { ToDeleteAppBar(navController) },
        content = { ToDeleteContent() },
        floatingActionButton = { ToDeleteFab() }
    )
}