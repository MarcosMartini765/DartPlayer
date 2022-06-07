package com.martini.dartplayer.presentation.components.dashboard.song

import androidx.compose.foundation.layout.*
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
import com.martini.dartplayer.domain.usecases.LoadSongsState
import com.martini.dartplayer.presentation.components.dashboard.LoadMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.viewModels.SearchTextViewModel
import com.martini.dartplayer.presentation.components.settings.local.ScanMusicButton

@ExperimentalMaterialApi
@Composable
fun DashboardSongsContent(
    onClick: () -> Unit,
    model: LoadMusicViewModel = hiltViewModel(),
    searchTextViewModel: SearchTextViewModel = hiltViewModel()
) {
    when (val state = model.state.value) {
        is LoadSongsState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is LoadSongsState.Loaded -> {
            val songs = state.music.songs

            if (songs.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.noSongsFound))
                        Spacer(Modifier.size(16.dp))
                        if (searchTextViewModel.state.value.isEmpty()) {
                            ScanMusicButton()
                        }
                    }
                }
            } else {
                SongsLazyColumn(
                    songs = songs,
                    onClick = onClick
                )
            }
        }
        is LoadSongsState.Failure -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(stringResource(R.string.somethingWentWrong))
            }
        }
        else -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}