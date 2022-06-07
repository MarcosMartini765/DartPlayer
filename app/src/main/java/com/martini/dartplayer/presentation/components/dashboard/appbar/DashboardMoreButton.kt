package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.presentation.components.AddSelectedToPlaylist
import com.martini.dartplayer.presentation.components.MoreButton
import com.martini.dartplayer.presentation.components.SearchViewModel

@ExperimentalMaterialApi
@Composable
fun DashboardMoreButton(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    if (searchViewModel.state.value) return

    MoreButton {
        AddSelectedToPlaylist()
        DashboardExitApp()
        SongSortOrder()
    }
}