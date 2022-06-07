package com.martini.dartplayer.presentation.components.playlist.appBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel

@Composable
fun PlaylistAppBarNav(
    selectedMusicViewModel: SelectedMusicViewModel = hiltViewModel(),
    popBack: () -> Unit
) {
    val selected = selectedMusicViewModel.state.value.music

    if (selected.isEmpty()) {
        val description = stringResource(id = R.string.back)
        IconButton(onClick = { popBack() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = description)
        }
        return
    }
    val description = stringResource(id = R.string.clear)
    IconButton(onClick = { selectedMusicViewModel.clear() }) {
        Icon(Icons.Filled.Clear, contentDescription = description)
    }
}