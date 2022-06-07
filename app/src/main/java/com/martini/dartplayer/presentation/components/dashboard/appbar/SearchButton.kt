package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.SearchViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel

@Composable
fun SearchButton(
    selected: SelectedMusicViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    if (searchViewModel.state.value) return

    if (selected.state.value.music.isEmpty()) {
        IconButton(onClick = { searchViewModel.set(true) }) {
            Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.search))
        }
    }
}