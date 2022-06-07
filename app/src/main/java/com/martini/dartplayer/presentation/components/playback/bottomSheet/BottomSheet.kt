package com.martini.dartplayer.presentation.components.playback.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R

@ExperimentalMaterialApi
@Composable
fun PlaybackBottomSheet(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(50.dp, 500.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = { onClick() },
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                stringResource(id = R.string.UpNext),
                style = MaterialTheme.typography.h6
            )
        }
        Divider()
        PlaybackLazyColumn()
    }
}