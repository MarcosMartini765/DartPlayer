package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import javax.inject.Inject

class DeleteArtistApi @Inject constructor(
    private val musicDao: MusicDao,
    private val playlistDao: PlaylistDao
) {
    suspend operator fun invoke(name: String) {
        val albums = musicDao.getArtistWithAlbums(name).albums.map {
            musicDao.getAlbumWithSongs(it.albumName)
        }

        for (album in albums) {
            musicDao.deleteSongs(album.songs)
            musicDao.deleteAlbums(listOf(album.album))
            musicDao.deleteAlbumArtistCrossRefs(album.album.albumName)
            playlistDao.deleteSongsFromPlaylists(album.songs.map { it.id })
        }

        musicDao.deleteArtist(name)
    }
}