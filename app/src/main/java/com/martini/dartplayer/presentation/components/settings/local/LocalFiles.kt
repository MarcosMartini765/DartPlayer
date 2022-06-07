package com.martini.dartplayer.presentation.components.settings.local

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R

@ExperimentalMaterialApi
@Composable
fun LocalFilesSettings() {
    Column {
        Text(
            stringResource(R.string.localFiles), style = TextStyle(
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        ))
        Spacer(modifier = Modifier.size(16.dp))
        ScanMusicButton()
        Divider()
        ScheduleScan()
    }
}