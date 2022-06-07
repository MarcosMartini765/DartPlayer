package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistAlbumCrossRef
import javax.inject.Inject

class DeleteAlbumsForArtistApi @Inject constructor(
    private val musicDao: MusicDao
) {
    suspend operator fun invoke(
        artistName: String,
        albums: List<AlbumWithSongs>
    ) {
        val crossRefs = mutableListOf<ArtistAlbumCrossRef>()

        for (album in albums) {
            crossRefs += ArtistAlbumCrossRef(
                artistName = artistName,
                albumName = album.album.albumName
            )
            musicDao.deleteSongs(album.songs)
        }

        musicDao.deleteListOfArtistAlbumCrossRef(crossRefs)
    }
}