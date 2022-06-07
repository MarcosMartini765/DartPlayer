package com.martini.dartplayer.presentation.components.albumForArtist.bottomSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.song.GetSongInfoState
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel

@ExperimentalMaterialApi
@Composable
fun AlbumForArtistBottomSheetContent(
    getSongInfoViewModel: GetSongInfoViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    when (val state = getSongInfoViewModel.state.value) {
        is GetSongInfoState.LoadedSong -> {
            AlbumForArtistBottomSheetSong(
                state.song,
                onClick = onClick
            )
        }
        is GetSongInfoState.Loading -> {
            Box(
                Modifier.defaultMinSize(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {
            Box(
                Modifier.defaultMinSize(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.errorGettingMusicData))
            }
        }
    }
}