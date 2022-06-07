package com.martini.dartplayer.presentation.components.playback.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.presentation.components.dashboard.song.SongArt

@ExperimentalMaterialApi
@Composable
fun PlaybackSongTile(
    song: Song,
    playing: Boolean,
    onClick: (song: Song) -> Unit,
    modifier: Modifier,
    playNext: () -> Unit,
    removeSong: () -> Unit
) {
    ListItem(
        modifier = modifier
            .background(
                color = if (playing) {
                    MaterialTheme.colors.secondary
                } else {
                    Color.Unspecified
                }
            )
            .clickable { onClick(song) },
        icon = { SongArt(song) },
        text = {
            Text(
                song.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        },
        secondaryText = {
            Text(
                song.artist, maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailing = {
            PlaybackSongDropDown(
                playNext = playNext,
                removeSong = removeSong
            )
        }
    )
}