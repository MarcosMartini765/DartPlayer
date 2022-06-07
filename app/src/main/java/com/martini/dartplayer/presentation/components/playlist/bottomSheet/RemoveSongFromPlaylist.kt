package com.martini.dartplayer.presentation.components.playlist.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.presentation.components.playlist.viewModels.RemoveSongFromPlaylistVIewModel

@ExperimentalMaterialApi
@Composable
fun RemoveSongFromPlaylist(
    song: Song,
    onClick: () -> Unit,
    removeSongFromPlaylistVIewModel: RemoveSongFromPlaylistVIewModel = hiltViewModel(),
    playlistWithSongs: PlaylistWithSongs
) {

    val context = LocalContext.current

    fun remove() {
        playSoundOnTap(context)
        removeSongFromPlaylistVIewModel(Selected(listOf(song)), playlistWithSongs)
        onClick()
    }

    val description = stringResource(id = R.string.RemoveFromPlaylist)

    ListItem(
        modifier = Modifier.clickable { remove() },
        icon = { Icon(Icons.Filled.Remove, contentDescription = description) },
        text = { Text(description) }
    )
}