package com.martini.dartplayer.presentation.components.dashboard.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.domain.entity.song.Song

@ExperimentalMaterialApi
@Composable
fun SongTile(
    song: Song,
    onMenuClick: (song: Song) -> Unit,
    onLongPress: (song: Song) -> Unit,
    onTap: (song: Song) -> Unit,
    selected: Boolean
) {

    Surface(
        color = if (selected) MaterialTheme.colors.secondary else Color.Transparent,
        modifier = Modifier.clickable { }
    ) {
        ListItem(
            modifier = Modifier
                .pointerInput(null) {
                    detectTapGestures(
                        onLongPress = { onLongPress(song) },
                        onTap = { onTap(song) }
                    )
                },
            icon = { SongArt(song) },
            text = {
                Text(
                    song.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            },
            secondaryText = {
                Text(
                    song.artist,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            },
            trailing = { SongMenu(onClick = { onMenuClick(song) }) }
        )
    }
}