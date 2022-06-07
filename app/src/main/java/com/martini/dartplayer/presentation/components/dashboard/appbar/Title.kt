package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.SearchViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel

@Composable
fun DashboardAppBarTitle(
    selected: SelectedMusicViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    when {
        selected.state.value.music.isNotEmpty() -> {
            Text(selected.state.value.music.count().toString())
        }
        searchViewModel.state.value -> {
            DashboardSearch()
        }
        else -> {
            Text(stringResource(R.string.app_name))
        }
    }
}