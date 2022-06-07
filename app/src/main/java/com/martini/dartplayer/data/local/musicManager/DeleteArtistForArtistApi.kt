package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import javax.inject.Inject

class DeleteArtistForArtistApi @Inject constructor(
    private val musicDao: MusicDao,
    private val playlistDao: PlaylistDao
) {
    suspend operator fun invoke(name: String) {
        val artist = musicDao.getArtistWithAlbums(name)

        for (album in artist.albums) {
            musicDao.deleteAlbumArtistCrossRefs(album.albumName)
            val albumWithSongs = musicDao.getAlbumWithSongs(album.albumName)
            val songsToDelete = albumWithSongs.songs.filter { it.artist == name }
            musicDao.deleteSongs(songsToDelete)
            playlistDao.deleteSongsFromPlaylists(songsToDelete.map { it.id })
        }
    }
}