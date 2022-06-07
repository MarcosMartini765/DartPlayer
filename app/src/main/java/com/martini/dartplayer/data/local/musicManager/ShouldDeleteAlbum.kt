package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import javax.inject.Inject

class ShouldDeleteAlbum @Inject constructor(
    private val musicDao: MusicDao
) {
    suspend operator fun invoke(name: String): Boolean {
        return musicDao.getAlbumWithSongs(name).songs.isEmpty()
    }
}