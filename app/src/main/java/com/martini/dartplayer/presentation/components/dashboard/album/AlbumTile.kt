package com.martini.dartplayer.presentation.components.dashboard.album

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs

@ExperimentalMaterialApi
@Composable
fun AlbumTile(
    albumWithSongs: AlbumWithSongs,
    onClick: (albumWithSongs: AlbumWithSongs) -> Unit,
    selected: Boolean,
    onTap: (albumWithSongs: AlbumWithSongs) -> Unit,
    onLongPress: (albumWithSongs: AlbumWithSongs) -> Unit
) {
    Surface(
        color = if (selected) MaterialTheme.colors.secondary else Color.Transparent
    ) {
        ListItem(
            modifier = Modifier.pointerInput(null) {
                detectTapGestures(
                    onLongPress = { onLongPress(albumWithSongs) },
                    onTap = { onTap(albumWithSongs) }
                )
            },
            icon = {
                AlbumArt(albumWithSongs)
            },
            text = {
                Text(
                    albumWithSongs.album.albumName, overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            },
            secondaryText = { Text("${albumWithSongs.songs.size} ${stringResource(R.string.song)}(s)") },
            trailing = { AlbumMenu(onClick = { onClick(albumWithSongs) }) }
        )
    }
}