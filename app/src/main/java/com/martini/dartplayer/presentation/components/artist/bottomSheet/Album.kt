package com.martini.dartplayer.presentation.components.artist.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.presentation.components.dashboard.album.sheet.AlbumSheetBanner
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlayAlbum
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlayNextAlbums
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlayNextAlbumsShuffled

@ExperimentalMaterialApi
@Composable
fun ArtistBottomSheetAlbum(
    album: AlbumWithSongAndArtists,
    onClick: () -> Unit
) {
    Column {
        AlbumSheetBanner(album)
        Divider()
        PlayAlbum(
            albumWithSongs = album.songs,
            onClick = onClick
        )
        PlayNextAlbums(
            albumWithSongs = album.songs,
            onClick = onClick
        )
        PlayNextAlbumsShuffled(
            albumWithSongs = album.songs,
            onClick = onClick
        )
        ArtistBottomSheetDeleteAlbum(onClick = onClick, album = album.songs)
    }
}