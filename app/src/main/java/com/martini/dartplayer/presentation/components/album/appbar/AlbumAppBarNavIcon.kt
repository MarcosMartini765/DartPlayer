package com.martini.dartplayer.presentation.components.album.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel

@Composable
fun AlbumAppBarNavIcon(
    navController: NavController,
    selected: SelectedMusicViewModel = hiltViewModel(),
) {
    when {
        selected.state.value.music.isNotEmpty() -> {
            IconButton(onClick = { selected.clear() }) {
                Icon(Icons.Filled.Clear, contentDescription = stringResource(R.string.clear))
            }
        }
        else -> {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
            }
        }
    }
}