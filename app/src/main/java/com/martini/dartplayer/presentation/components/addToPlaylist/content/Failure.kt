package com.martini.dartplayer.presentation.components.addToPlaylist.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R

@Composable
fun AddToPlaylistContentFailure() {

    val description = stringResource(id = R.string.Error)

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(description)
        Spacer(modifier = Modifier.height(16.dp))
        Icon(Icons.Filled.Error, contentDescription = description)
    }
}