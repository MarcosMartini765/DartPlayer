package com.martini.dartplayer.data.local

import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import com.martini.dartplayer.domain.entity.playlist.Playlist
import com.martini.dartplayer.domain.entity.playlist.SongDateCrossRef
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistSongCrossRef
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.AddToPlaylistParams
import javax.inject.Inject

class PlaylistApi @Inject constructor(
    private val playlistDao: PlaylistDao,
    private val musicDao: MusicDao
) {

    suspend fun getPlaylist(name: String): PlaylistWithSongs {
        val refs = playlistDao.getCrossRefsForPlaylist(name)

        val playlist = playlistDao.getPlaylist(name)

        val songs = playlist.songs.associateBy { it.id }

        val sortedSongs = refs.mapNotNull { songs[it.id] }

        return PlaylistWithSongs(
            playlist = playlist.playlist,
            songs = sortedSongs
        )
    }

    suspend fun addToPlaylist(params: AddToPlaylistParams) {
        for (entity in params.selected.music) {
            when (entity) {
                is Song -> addSongToPlaylist(entity, params.playlist)
                is AlbumWithSongs -> addAlbumToPlaylist(entity, params.playlist)
                is ArtistWithAlbums -> addArtistToPlaylist(entity, params.playlist)
            }
        }
    }

    suspend fun createPlaylist(name: String) {
        playlistDao.createPlaylist(Playlist(playlistName = name))
    }

    suspend fun deletePlaylist(playlistWithSongs: PlaylistWithSongs) {
        playlistDao.deleteSongDateCrossRefForPlaylist(playlistWithSongs.playlist.playlistName)
        playlistDao.deletePlaylist(playlistWithSongs.playlist)
        playlistDao.deleteCrossForPlaylist(playlistWithSongs.playlist.playlistName)
    }

    suspend fun removeSongFromPlaylist(selected: Selected, playlist: PlaylistWithSongs) {
        for (music in selected.music) {
            when (music) {
                is Song -> removeSong(music, playlist)
                is AlbumWithSongs -> removeAlbum(music, playlist)
                is ArtistWithAlbums -> removeArtist(music, playlist)
            }
        }
    }

    private suspend fun removeArtist(artist: ArtistWithAlbums, playlist: PlaylistWithSongs) {
        val albums = artist.albums.map { musicDao.getAlbumWithSongs(it.albumName) }

        albums.forEach { removeAlbum(it, playlist) }
    }

    private suspend fun removeAlbum(album: AlbumWithSongs, playlist: PlaylistWithSongs) {

        val crossRef = mutableListOf<PlaylistSongCrossRef>()
        val songDateCrossRef = mutableListOf<SongDateCrossRef>()

        for (song in album.songs) {
            crossRef += PlaylistSongCrossRef(
                playlistName = playlist.playlist.playlistName,
                id = song.id
            )
            songDateCrossRef += SongDateCrossRef(
                id = song.id,
                playlist = playlist.playlist.playlistName
            )
        }

        playlistDao.deleteSongDateCrossRefs(songDateCrossRef)
        playlistDao.deleteCrossRefs(crossRef)
    }

    private suspend fun removeSong(song: Song, playlist: PlaylistWithSongs) {
        val crossRef = mutableListOf<PlaylistSongCrossRef>()
        val songDateCrossRef = mutableListOf<SongDateCrossRef>()

        crossRef += PlaylistSongCrossRef(
            playlistName = playlist.playlist.playlistName,
            id = song.id
        )

        songDateCrossRef += SongDateCrossRef(
            id = song.id,
            playlist = playlist.playlist.playlistName
        )

        playlistDao.deleteSongDateCrossRefs(songDateCrossRef)
        playlistDao.deleteCrossRefs(crossRef)
    }

    private suspend fun addSongToPlaylist(song: Song, playlist: Playlist) {
        playlistDao.insertCrossRefs(
            listOf(
                PlaylistSongCrossRef(
                    playlistName = playlist.playlistName,
                    id = song.id
                )
            )
        )
        playlistDao.insertSongDateCrossRefs(
            listOf(
                SongDateCrossRef(
                    id = song.id,
                    playlist = playlist.playlistName,
                    dateAdded = System.currentTimeMillis()
                )
            )
        )
    }

    private suspend fun addAlbumToPlaylist(
        albumWithSongs: AlbumWithSongs,
        playlist: Playlist
    ) {
        val songDateCrossRefs = albumWithSongs.songs.map {
            SongDateCrossRef(
                id = it.id,
                playlist = playlist.playlistName,
                dateAdded = System.currentTimeMillis()
            )
        }

        val crossRefs = albumWithSongs.songs.map {
            PlaylistSongCrossRef(
                playlistName = playlist.playlistName,
                id = it.id
            )
        }

        playlistDao.insertSongDateCrossRefs(songDateCrossRefs)
        playlistDao.insertCrossRefs(crossRefs)
    }

    private suspend fun addArtistToPlaylist(
        artistWithAlbums: ArtistWithAlbums,
        playlist: Playlist
    ) {

        val crossRefs = mutableListOf<PlaylistSongCrossRef>()
        val songDateCrossRef = mutableListOf<SongDateCrossRef>()

        artistWithAlbums.albums.map {
            musicDao.getAlbumWithSongs(it.albumName)
        }.forEach { album ->
            album.songs.forEach {
                crossRefs += PlaylistSongCrossRef(
                    playlistName = playlist.playlistName,
                    id = it.id
                )
                songDateCrossRef += SongDateCrossRef(
                    id = it.id,
                    playlist = playlist.playlistName,
                    dateAdded = System.currentTimeMillis()
                )
            }
        }
        playlistDao.insertSongDateCrossRefs(songDateCrossRef)
        playlistDao.insertCrossRefs(crossRefs)
    }
}