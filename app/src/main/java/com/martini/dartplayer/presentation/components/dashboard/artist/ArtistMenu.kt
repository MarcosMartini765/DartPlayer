package com.martini.dartplayer.presentation.components.dashboard.artist

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap

@Composable
fun ArtistMenu(
    onClick: () -> Unit
) {
    val context = LocalContext.current

    IconButton(onClick = {
        playSoundOnTap(context)
        onClick()
    }) {
        Icon(Icons.Filled.MoreVert, contentDescription = stringResource(R.string.more))
    }
}