package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.presentation.components.dashboard.viewModels.SessionViewModel

@Composable
fun DashboardSongBanner(
    sessionViewModel: SessionViewModel = hiltViewModel(),
    navigateToPlaybackScreen: () -> Unit
) {
    if (!sessionViewModel.state.value) return

    Surface(
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DashboardBottomAppBarSongInfo(
                navigateToPlaybackScreen = navigateToPlaybackScreen
            )
            DashboardAppBarControls()
        }
    }
}