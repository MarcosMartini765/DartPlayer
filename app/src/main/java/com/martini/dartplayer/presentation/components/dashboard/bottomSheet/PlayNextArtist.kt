package com.martini.dartplayer.presentation.components.dashboard.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.params.player.PlayNextParams
import com.martini.dartplayer.presentation.components.artist.viewModels.PlayNextArtistViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardPlayNextArtist(
    artistWithAlbums: ArtistWithAlbums,
    onClick: () -> Unit,
    playNextArtistViewModel: PlayNextArtistViewModel = hiltViewModel()
) {

    fun play() {
        playNextArtistViewModel(
            PlayNextParams(
                selected = Selected(listOf(artistWithAlbums)),
            )
        )
        onClick()
    }

    ListItem(
        modifier = Modifier.clickable { play() },
        icon = {
            Icon(
                Icons.Filled.SkipNext,
                contentDescription = stringResource(R.string.playNext)
            )
        },
        text = { Text(stringResource(R.string.playNext)) }
    )
}