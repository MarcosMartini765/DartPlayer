package com.martini.dartplayer.presentation.components.dashboard.artist

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.DashboardBottomSheetDeleteArtist
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.DashboardPlayNextArtist
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.DashboardPlayNextArtistShuffled

@ExperimentalMaterialApi
@Composable
fun ArtistBottomSheet(
    artistWithAlbums: ArtistWithAlbums,
    onClick: () -> Unit
) {
    Column {
        ListItem(
            icon = {
                Icon(
                    Icons.Filled.ArtTrack,
                    contentDescription = stringResource(R.string.artist)
                )
            },
            text = {
                Text(
                    artistWithAlbums.artist.artistName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            },
            secondaryText = {
                Text(
                    "${artistWithAlbums.albums.count()} ${stringResource(R.string.album)}(s) * ${
                        artistWithAlbums.albums.joinToString(
                            ", "
                        ) { it.albumName }
                    }",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
        Divider()
        DashboardPlayArtist(
            artistWithAlbums = artistWithAlbums,
            onClick = onClick
        )
        DashboardPlayNextArtist(
            artistWithAlbums = artistWithAlbums,
            onClick = onClick
        )
        DashboardPlayNextArtistShuffled(
            artistWithAlbums = artistWithAlbums,
            onClick = onClick
        )
        DashboardBottomSheetDeleteArtist(artist = artistWithAlbums, onClick = onClick)
    }
}