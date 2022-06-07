package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.Screen
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel

@ExperimentalMaterialApi
@Composable
fun DashboardAppBarDelete(
    navController: NavController,
    selected: SelectedMusicViewModel = hiltViewModel(),
) {
    if (selected.state.value.music.isNotEmpty()) {
        IconButton(onClick = { navController.navigate(Screen.ToDelete.route) }) {
            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete))
        }
    }
}