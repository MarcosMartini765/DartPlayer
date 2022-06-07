package com.martini.dartplayer.presentation.components.toDelete

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.DeleteMusicState
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.DeleteMusicViewModel

@Composable
fun ToDeleteAppBar(
    navController: NavController,
    deleteMusic: DeleteMusicViewModel = hiltViewModel(),
    selectedMusic: SelectedMusicViewModel = hiltViewModel()
) {

    if (selectedMusic.state.value.music.isEmpty()) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                when (deleteMusic.state.value) {
                    is DeleteMusicState.Loading -> {}
                    else -> {
                        navController.popBackStack()
                    }
                }
            }) {
                Icon(Icons.Filled.Clear, contentDescription = stringResource(R.string.back))
            }
        },
        title = { Text(stringResource(R.string.toDelete)) },
    )
}