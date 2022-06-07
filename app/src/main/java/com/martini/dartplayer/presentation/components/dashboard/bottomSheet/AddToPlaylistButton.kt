package com.martini.dartplayer.presentation.components.dashboard.bottomSheet


import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.NavigationViewModel

@ExperimentalMaterialApi
@Composable
fun AddToPlaylistButton(
    navigationViewModel: NavigationViewModel = hiltViewModel(),
    onClick: () -> Unit
) {

    val description = stringResource(id = R.string.AddToPlaylist)

    ListItem(
        modifier = Modifier.clickable {
            navigationViewModel.navigateToAddPlaylist()
            onClick()
        },
        icon = {
            Icon(
                Icons.Filled.PlaylistAdd,
                contentDescription = description
            )
        },
        text = { Text(description) }
    )
}