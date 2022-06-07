package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.SearchViewModel
import com.martini.dartplayer.presentation.components.dashboard.LoadMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.viewModels.SearchTextViewModel

@Composable
fun DashboardLeaveSearch(
    loadMusicViewModel: LoadMusicViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    searchTextViewModel: SearchTextViewModel = hiltViewModel()
) {
    IconButton(onClick = {
        searchViewModel.set(false)
        searchTextViewModel("")
        loadMusicViewModel.silentLoad()
    }) {
        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
    }
}