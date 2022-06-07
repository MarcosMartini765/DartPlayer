package com.martini.dartplayer.data.local

import android.content.Intent
import com.martini.dartplayer.data.local.musicManager.MusicAdderApi
import com.martini.dartplayer.data.local.musicManager.MusicDeleterApi
import com.martini.dartplayer.data.local.musicManager.MusicGetterApi
import com.martini.dartplayer.data.local.musicManager.MusicOpenIntentParser
import com.martini.dartplayer.data.model.SongResource
import com.martini.dartplayer.data.model.toSong
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.albumForArtist.DeleteAlbumForArtistParams

class LocalMusicApi(
    private val musicDao: MusicDao,
    private val musicAdderApi: MusicAdderApi,
    private val musicGetterApi: MusicGetterApi,
    private val musicOpenIntentParser: MusicOpenIntentParser,
    private val musicDeleterApi: MusicDeleterApi
) {
    suspend fun getMusic(): MutableList<SongResource> {

        val songs = musicGetterApi()

        val songList = songs.map { it.toSong() }

        musicAdderApi.addSongs(songList)

        musicAdderApi.deleteGhostSongs(songList)

        musicAdderApi.insertAlbums()

        musicAdderApi.insertArtists()

        musicAdderApi.insertArtistAlbumCrossRef()

        return songs
    }

    suspend fun getSongForUri(intent: Intent): Song {
        val songId = musicOpenIntentParser(intent)

        val songList = listOf(musicGetterApi.getForId(songId).toSong())

        musicAdderApi.addSongs(songList)
        musicAdderApi.deleteGhostSongs(songList)
        musicAdderApi.insertAlbums()
        musicAdderApi.insertArtists()
        musicAdderApi.insertArtistAlbumCrossRef()

        return musicDao.getSongForId(songId)
    }

    suspend fun deleteMusic(selected: Selected) {
        selected.music.forEach {
            when (it) {
                is Song -> musicDeleterApi.deleteSongFromCache(it)
                is AlbumWithSongs -> musicDeleterApi.deleteAlbum(it)
                is ArtistWithAlbums -> musicDeleterApi.deleteArtist(it)
                is PlaylistWithSongs -> musicDeleterApi.deletePlaylist(it)
            }
        }
    }

    suspend fun deleteAlbumsForArtist(
        artist: ArtistWithAlbumsAndSongs,
        albums: List<AlbumWithSongs>
    ) {
        musicDeleterApi.deleteAlbumsForArtist(artist.name, albums)
    }

    suspend fun deleteArtistForArtist(artist: ArtistWithAlbumsAndSongs) {
        musicDeleterApi.deleteArtistForArtist(artist)
    }

    suspend fun deleteSongsForAlbum(songs: List<Song>) {
        musicDeleterApi.deleteSongsForAlbum(songs)
    }

    suspend fun deleteAlbumForAlbum(albumWithSongs: AlbumWithSongs) {
        musicDeleterApi.deleteAlbumForAlbum(albumWithSongs)
    }

    suspend fun deleteAlbumForArtist(deleteAlbumForArtistParams: DeleteAlbumForArtistParams) {
        musicDeleterApi.deleteAlbumsForArtist(
            artist = deleteAlbumForArtistParams.artistName,
            albums = listOf(deleteAlbumForArtistParams.albumWithSongs)
        )
    }
}