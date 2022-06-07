package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import javax.inject.Inject

class DeletePlaylistApi @Inject constructor(
    private val playlistDao: PlaylistDao
) {
    suspend operator fun invoke(playlistWithSongs: PlaylistWithSongs) {
        playlistDao.deletePlaylist(playlistWithSongs.playlist)
        playlistDao.deleteCrossForPlaylist(playlistWithSongs.playlist.playlistName)
        playlistDao.deleteSongDateCrossRefForPlaylist(playlistWithSongs.playlist.playlistName)
    }
}