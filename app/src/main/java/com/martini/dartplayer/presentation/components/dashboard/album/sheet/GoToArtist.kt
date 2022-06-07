package com.martini.dartplayer.presentation.components.dashboard.album.sheet

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.presentation.components.NavigationViewModel

@ExperimentalMaterialApi
@Composable
fun AlbumSheetGoToArtist(
    album: AlbumWithSongAndArtists,
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    if (album.artists.artists.count() < 2) {
        ListItem(
            modifier = Modifier.clickable {
                album.artists.artists.firstOrNull()?.let {
                    navigationViewModel.navigateToArtist(it.artistName)
                }
            },
            icon = {
                Icon(
                    Icons.Filled.ArtTrack,
                    contentDescription = stringResource(R.string.artist)
                )
            },
            text = { Text(stringResource(R.string.goToArtist)) }
        )
    }
}