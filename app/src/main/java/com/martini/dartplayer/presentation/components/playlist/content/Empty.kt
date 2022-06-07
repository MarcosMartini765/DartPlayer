package com.martini.dartplayer.presentation.components.playlist.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R

@Composable
fun PlaylistContentEmpty() {
    val description = stringResource(id = R.string.noSongsFound)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Audiotrack,
            contentDescription = description,
            Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = description)
    }
}