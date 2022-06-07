package com.martini.dartplayer.presentation.components.dashboard.album.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.DashboardBottomSheetDeleteAlbum
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlayAlbum
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlayNextAlbums
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlayNextAlbumsShuffled

@ExperimentalMaterialApi
@Composable
fun AlbumBottomSheet(
    album: AlbumWithSongAndArtists,
    goToArtist: (name: String) -> Unit,
    onClick: () -> Unit
) {
    Column {
        AlbumSheetBanner(album)
        Divider()
        AlbumSheetChips(album, goToArtist = goToArtist)
        PlayAlbum(albumWithSongs = album.songs, onClick = onClick)
        PlayNextAlbums(
            albumWithSongs = album.songs,
            onClick = onClick
        )
        PlayNextAlbumsShuffled(
            albumWithSongs = album.songs,
            onClick = onClick
        )
        AlbumSheetGoToArtist(album)
        DashboardBottomSheetDeleteAlbum(album = album.songs, onClick = onClick)
    }
}