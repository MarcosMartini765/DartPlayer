package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import javax.inject.Inject

class DeleteAlbumForAlbumApi @Inject constructor(
    private val musicDao: MusicDao,
    private val playlistDao: PlaylistDao
) {
    suspend operator fun invoke(albumWithSongs: AlbumWithSongs) {
        musicDao.deleteSongs(albumWithSongs.songs)

        musicDao.deleteAlbumArtistCrossRefs(albumWithSongs.album.albumName)

        musicDao.deleteAlbums(listOf(albumWithSongs.album))

        playlistDao.deleteSongsFromPlaylists(albumWithSongs.songs.map { it.id })

        val artists = musicDao.getArtistsWithAlbums()

        musicDao.deleteArtists(artists.filter { it.albums.isEmpty() }.map { it.artist })
    }
}