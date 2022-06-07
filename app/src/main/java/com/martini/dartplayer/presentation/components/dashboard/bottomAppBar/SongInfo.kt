package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardBottomAppBarSongInfo(
    navigateToPlaybackScreen: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth(0.5f)
            .clickable { navigateToPlaybackScreen() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        DashboardBottomAppBarSongArt()
        Spacer(modifier = Modifier.width(8.dp))
        DashboardBottomAppBarSongText()
    }
}