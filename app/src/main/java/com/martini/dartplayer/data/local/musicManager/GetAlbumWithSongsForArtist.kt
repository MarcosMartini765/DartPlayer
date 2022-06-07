package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import javax.inject.Inject

class GetAlbumWithSongsForArtist @Inject constructor(
    private val musicDao: MusicDao
) {
    suspend operator fun invoke(artistName: String): List<AlbumWithSongs> {
        val albums = musicDao.getArtistWithAlbums(artistName)

        val albumWithSongs = mutableListOf<AlbumWithSongs>()

        for (album in albums.albums) {
            albumWithSongs += musicDao.getAlbumWithSongs(album.albumName)
        }

        return albumWithSongs
    }
}