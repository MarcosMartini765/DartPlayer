package com.martini.dartplayer.presentation.components.artist.appbar

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.artist.LoadArtistState
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.artist.LoadArtistViewModel

@Composable
fun ArtistAppBarTitle(
    model: LoadArtistViewModel = hiltViewModel(),
    selected: SelectedMusicViewModel = hiltViewModel()
) {

    when {
        selected.state.value.music.isNotEmpty() -> {
            Text(selected.state.value.music.count().toString())
        }
        else -> {
            when (val state = model.state.value) {
                is LoadArtistState.Loaded -> {
                    Text(state.data.name)
                }
                is LoadArtistState.Failure -> {
                    Text(stringResource(R.string.Error))
                }
                else -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}