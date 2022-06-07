package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.artist.Artist
import com.martini.dartplayer.domain.entity.daos.MusicDao
import javax.inject.Inject

class GetArtistsForAlbumApi @Inject constructor(
    private val musicDao: MusicDao
) {
    suspend operator fun invoke(albumName: String): List<Artist> {
        return musicDao.getAlbumWithArtists(albumName).artists
    }
}