package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import javax.inject.Inject

class ShouldDeleteArtist @Inject constructor(
    private val musicDao: MusicDao
) {
    suspend operator fun invoke(name: String): Boolean {
        return musicDao.getArtistWithAlbums(name).albums.isEmpty()
    }
}