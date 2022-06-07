package com.martini.dartplayer.presentation.components.artist

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.params.player.StartNewSessionParams
import com.martini.dartplayer.domain.usecases.artist.LoadArtistState
import com.martini.dartplayer.presentation.components.artist.viewModels.PlayArtistViewModel

@Composable
fun ArtistActionButton(
    playArtistViewModel: PlayArtistViewModel = hiltViewModel(),
    loadArtistViewModel: LoadArtistViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    fun play() {
        when (val state = loadArtistViewModel.state.value) {
            is LoadArtistState.Loaded -> {
                playArtistViewModel(
                    StartNewSessionParams(
                        selected = Selected(listOf(state.data.albums))
                    )
                )
            }
            else -> {}
        }
    }

    FloatingActionButton(onClick = {
        playSoundOnTap(context)
        play()
    }) {
        Icon(
            Icons.Filled.Shuffle,
            contentDescription = stringResource(R.string.shuffleArtists),
            tint = if (MaterialTheme.colors.isLight) {
                Color.Black
            } else {
                Color.White
            }
        )
    }
}