package com.martini.dartplayer.presentation.components.playback.content

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlaybackContent() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaybackSongArt()
        Spacer(modifier = Modifier.height(16.dp))
        PlaybackSongText()
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlaybackRepeatMode()
            PlaybackBackButton()
            PlaybackPlayOrPause()
            PlaybackNextButton()
            PlaybackShuffle()
        }
        PlaybackPositionSlider()
        PlaybackTime()
    }
}