package com.martini.dartplayer.presentation.components.dashboard.artist

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums

@ExperimentalMaterialApi
@Composable
fun ArtistTile(
    artistWithAlbums: ArtistWithAlbums,
    onClick: (artistWithAlbums: ArtistWithAlbums) -> Unit,
    selected: Boolean,
    onTap: (artistWithAlbums: ArtistWithAlbums) -> Unit,
    onLongPress: (artistWithAlbums: ArtistWithAlbums) -> Unit
) {
    Surface(
        color = if (selected) MaterialTheme.colors.secondary else Color.Transparent
    ) {
        ListItem(
            modifier = Modifier.pointerInput(null) {
                detectTapGestures(
                    onTap = { onTap(artistWithAlbums) },
                    onLongPress = { onLongPress(artistWithAlbums) }
                )
            },
            icon = {
                Icon(
                    Icons.Filled.ArtTrack, contentDescription = stringResource(R.string.artist),
                    modifier = Modifier.size(48.dp)
                )
            },
            text = {
                Text(
                    artistWithAlbums.artist.artistName, overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            },
            secondaryText = {
                Text(
                    artistWithAlbums.albums.joinToString(", ") { it.albumName },
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            },
            trailing = { ArtistMenu(onClick = { onClick(artistWithAlbums) }) }
        )
    }
}