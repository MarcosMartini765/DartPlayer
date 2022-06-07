package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import javax.inject.Inject

class DeleteAlbumApi @Inject constructor(
    private val musicDao: MusicDao,
    private val playlistDao: PlaylistDao
){
    suspend operator fun invoke(name: String) {
        val albumWithSongs = musicDao.getAlbumWithSongs(name)

        musicDao.deleteSongs(albumWithSongs.songs)

        musicDao.deleteAlbums(listOf(albumWithSongs.album))

        musicDao.deleteAlbumArtistCrossRefs(name)

        playlistDao.deleteSongsFromPlaylists(albumWithSongs.songs.map { it.id })
    }
}