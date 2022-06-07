package com.martini.dartplayer.presentation.components.dashboard.artist

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.params.player.StartNewSessionParams
import com.martini.dartplayer.presentation.components.artist.viewModels.PlayArtistViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardPlayArtist(
    artistWithAlbums: ArtistWithAlbums,
    onClick: () -> Unit,
    playArtistViewModel: PlayArtistViewModel = hiltViewModel()
) {

    fun play() {
        playArtistViewModel(
            StartNewSessionParams(
                selected = Selected(listOf(artistWithAlbums))
            )
        )
        onClick()
    }

    ListItem(
        modifier = Modifier.clickable { play() },
        icon = { Icon(Icons.Filled.PlayArrow, contentDescription = stringResource(R.string.play)) },
        text = { Text(stringResource(R.string.play)) }
    )
}