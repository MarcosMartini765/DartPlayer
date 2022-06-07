package com.martini.dartplayer.presentation.components.playlist.bottomSheet

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
fun PlaylistBottomSheetContentFailure() {

    val description = stringResource(id = R.string.errorGettingMusicData)

    Column(
        Modifier.heightIn(200.dp, 400.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Filled.Error, contentDescription = description)
        Spacer(modifier = Modifier.height(16.dp))
        Text(description)
    }
}