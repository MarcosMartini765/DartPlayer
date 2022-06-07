package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import com.martini.dartplayer.domain.entity.song.Song
import javax.inject.Inject

class DeleteSongsForAlbumApi @Inject constructor(
    private val musicDao: MusicDao,
    private val playlistDao: PlaylistDao
) {
    suspend operator fun invoke(songs: List<Song>) {
        musicDao.deleteSongs(songs)
        playlistDao.deleteSongsFromPlaylists(songs.map { it.id })
    }
}