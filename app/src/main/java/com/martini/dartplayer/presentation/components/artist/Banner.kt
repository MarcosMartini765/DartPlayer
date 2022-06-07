package com.martini.dartplayer.presentation.components.artist

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.artist.LoadArtistState

@Composable
fun ArtistBanner(
    model: LoadArtistViewModel = hiltViewModel()
) {
    when (val state = model.state.value) {
        is LoadArtistState.Loaded -> {
            val artist = state.data
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        artist.name,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "${artist.albums.size} ${stringResource(R.string.album)}(s)",
                        style = MaterialTheme.typography.body2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "${
                            artist.albums.sumOf { it.songs.count() }
                        } ${stringResource(R.string.song)}(s)",
                        style = MaterialTheme.typography.overline
                    )
                }
            }
        }
        is LoadArtistState.Failure -> {
            Box(contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.Error))
            }
        }
        else -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}