package com.martini.dartplayer.presentation.components.settings.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R

@Composable
fun PlayerSettings() {
    Column {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            stringResource(R.string.PlayerSettings), style = TextStyle(
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        RespectAudioFocus()
        //Divider()
        //PlayerPlaybackMode()
    }
}