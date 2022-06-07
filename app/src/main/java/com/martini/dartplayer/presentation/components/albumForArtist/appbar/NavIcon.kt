package com.martini.dartplayer.presentation.components.albumForArtist.appbar

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
fun AlbumForArtistAppBarNavIcon(
    selectedMusic: SelectedMusicViewModel = hiltViewModel(),
    popBack: () -> Unit
) {
    val selected = selectedMusic.state.value.music

    if (selected.isNotEmpty()) {
        IconButton(onClick = { selectedMusic.clear() }) {
            Icon(Icons.Filled.Clear, contentDescription = stringResource(R.string.clear))
        }
    } else {
        IconButton(onClick = { popBack() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
        }
    }
}