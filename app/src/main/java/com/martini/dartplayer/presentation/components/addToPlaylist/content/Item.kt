package com.martini.dartplayer.presentation.components.addToPlaylist.content

import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs

@ExperimentalMaterialApi
@Composable
fun AddToPlaylistContentItem(
    playlistWithSongs: PlaylistWithSongs,
    contains: Boolean,
    onClick: (value: Boolean) -> Unit
) {
    ListItem(
        icon = { Checkbox(checked = contains, onCheckedChange = onClick) },
        text = {
            Text(
                text = playlistWithSongs.playlist.playlistName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}