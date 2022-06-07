package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.presentation.components.SearchViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel

@ExperimentalMaterialApi
@Composable
fun DashboardAppBarNavIcon(
    selected: SelectedMusicViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    when {
        selected.state.value.music.isNotEmpty() -> {
            DashboardAppBarNavIconSelected()
        }
        searchViewModel.state.value -> {
            DashboardLeaveSearch()
        }
        else -> {
            DashboardAppBarNavIconSelected()
        }
    }
}