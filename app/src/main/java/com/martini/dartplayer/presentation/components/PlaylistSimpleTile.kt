package com.martini.dartplayer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FeaturedPlayList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs

@ExperimentalMaterialApi
@Composable
fun PlaylistSimpleTile(
    playlist: PlaylistWithSongs,
    onClick: (playlist: PlaylistWithSongs) -> Unit
) {

    ListItem(
        Modifier.clickable { onClick(playlist) },
        text = {
            Text(
                playlist.playlist.playlistName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                Icons.Filled.FeaturedPlayList,
                contentDescription = stringResource(id = R.string.Playlists)
            )
        }
    )
}