package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardBottomAppBar(
    navigateToPlaybackScreen: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        DashboardSongBanner(
            navigateToPlaybackScreen = navigateToPlaybackScreen
        )
    }
}