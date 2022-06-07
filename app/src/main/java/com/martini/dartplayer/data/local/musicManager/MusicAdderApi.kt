package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.data.model.AlbumResource
import com.martini.dartplayer.data.model.ArtistResource
import com.martini.dartplayer.data.model.toAlbum
import com.martini.dartplayer.data.model.toArtist
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.relations.ArtistAlbumCrossRef
import com.martini.dartplayer.domain.entity.song.Song
import javax.inject.Inject

class MusicAdderApi @Inject constructor(
    private val musicDao: MusicDao
) {
    suspend fun addSongs(songs: List<Song>) {
        musicDao.insertSongs(songs)
    }

    suspend fun deleteGhostSongs(newSongs: List<Song>) {
        val cachedSongs = musicDao.getAllSongs()

        val songsToRemove = mutableListOf<Song>()

        cachedSongs.forEach { old ->
            newSongs.find { new ->
                old.id == new.id
            }.let {
                if (it == null) {
                    songsToRemove.add(old)
                }
            }

        }
        musicDao.deleteSongs(songsToRemove)
    }

    suspend fun insertAlbums() {
        val songs = musicDao.getAllSongs()

        val albumsName: List<String> = songs.map { it.album }.distinct()

        val albums = albumsName.map { AlbumResource(it).toAlbum() }

        musicDao.insertAlbums(albums)

        val cachedAlbums = musicDao.getAllAlbums()

        val albumsToDelete = cachedAlbums.minus(albums.toSet())

        musicDao.deleteAlbums(albumsToDelete)
    }

    suspend fun insertArtists() {
        val songs = musicDao.getAllSongs()

        val artistsName = songs.map { it.artist }.distinct()

        val artists = artistsName.map { ArtistResource(it).toArtist() }

        musicDao.insertArtists(artists)

        val cachedArtists = musicDao.getAllArtists()

        val artistsToDelete = cachedArtists.minus(artists.toSet())

        musicDao.deleteArtists(artistsToDelete)
    }

    suspend fun insertArtistAlbumCrossRef() {
        val songs = musicDao.getAllSongs()

        val crossRefs = mutableListOf<ArtistAlbumCrossRef>()

        songs.forEach {
            crossRefs.add(
                ArtistAlbumCrossRef(albumName = it.album, artistName = it.artist)
            )
        }

        musicDao.insertListOfArtistAlbumCrossRef(crossRefs)

        val cachedRefs = musicDao.getAllArtistAlbumCrossRefs()

        val refsToDelete = cachedRefs.minus(crossRefs.toSet())

        musicDao.deleteListOfArtistAlbumCrossRef(refsToDelete)
    }
}