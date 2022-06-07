package com.martini.dartplayer.presentation.components.dashboard.playlists.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.addToPlaylist.content.AddToPlaylistActionButton
import com.martini.dartplayer.presentation.components.dashboard.viewModels.SearchTextViewModel

@Composable
fun PlaylistEmpty(
    searchTextViewModel: SearchTextViewModel = hiltViewModel()
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.noPlaylistsFound))
        if (searchTextViewModel.state.value.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            AddToPlaylistActionButton(
                description = stringResource(id = R.string.CreatePlaylist),
                floating = false
            )
        }
    }
}