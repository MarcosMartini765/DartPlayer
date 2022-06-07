package com.martini.dartplayer.presentation.components.dashboard.playlists.content.tile

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs

@ExperimentalMaterialApi
@Composable
fun PlaylistTile(
    playlist: PlaylistWithSongs,
    selected: Boolean,
    onTap: (playlist: PlaylistWithSongs) -> Unit,
    onLongPress: (playlist: PlaylistWithSongs) -> Unit,
    onMenuClick: () -> Unit
) {

    val qtd = playlist.songs.count()
    val song = stringResource(id = R.string.song)
    val songs = stringResource(id = R.string.Songs)

    ListItem(
        modifier = Modifier
            .background(
                color = if (selected) {
                    MaterialTheme.colors.secondary
                } else {
                    Color.Unspecified
                }
            )
            .pointerInput(null) {
                detectTapGestures(
                    onTap = { onTap(playlist) },
                    onLongPress = { onLongPress(playlist) }
                )
            },
        icon = { PlaylistArt(playlist) },
        text = {
            Text(
                playlist.playlist.playlistName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        secondaryText = {
            Text("$qtd ${if (qtd > 1) songs else song}")
        },
        trailing = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(Icons.Filled.MoreVert, contentDescription = stringResource(id = R.string.more))
            }
        }
    )
}