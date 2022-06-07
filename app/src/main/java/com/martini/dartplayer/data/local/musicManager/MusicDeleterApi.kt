package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import javax.inject.Inject

class MusicDeleterApi @Inject constructor(
    private val removeSongFromPlaylistApi: RemoveSongFromPlaylistApi,
    private val shouldDeleteAlbum: ShouldDeleteAlbum,
    private val shouldDeleteArtist: ShouldDeleteArtist,
    private val deleteSongApi: DeleteSongApi,
    private val deleteAlbumApi: DeleteAlbumApi,
    private val deleteArtistApi: DeleteArtistApi,
    private val deletePlaylistApi: DeletePlaylistApi,
    private val getArtistsForAlbumApi: GetArtistsForAlbumApi,
    private val getAlbumWithSongsForArtist: GetAlbumWithSongsForArtist,
    private val deleteAlbumsForArtistApi: DeleteAlbumsForArtistApi,
    private val deleteArtistForArtistApi: DeleteArtistForArtistApi,
    private val deleteSongsForAlbumApi: DeleteSongsForAlbumApi,
    private val deleteAlbumForAlbumApi: DeleteAlbumForAlbumApi
) {
    suspend fun deleteSongFromCache(song: Song) {
        deleteSongApi(song)

        if (shouldDeleteAlbum(song.album)) {
            deleteAlbumApi(song.album)
        }

        if (shouldDeleteArtist(song.artist)) {
            deleteArtistApi(song.artist)
        }

        removeSongFromPlaylistApi(listOf(song))
    }

    suspend fun deleteAlbum(albumWithSongs: AlbumWithSongs) {
        deleteAlbumApi(albumWithSongs.album.albumName)

        for (artist in getArtistsForAlbumApi(albumWithSongs.album.albumName)) {
            if (shouldDeleteArtist(artist.artistName)) {
                deleteArtistApi(artist.artistName)
            }
        }

        removeSongFromPlaylistApi(albumWithSongs.songs)
    }

    suspend fun deleteArtist(artistWithAlbums: ArtistWithAlbums) {
        val albumsWithSongs = getAlbumWithSongsForArtist(artistWithAlbums.artist.artistName)

        val songs = mutableListOf<Song>()

        for (albumWithSongs in albumsWithSongs) {
            songs += albumWithSongs.songs
        }

        deleteArtistApi(artistWithAlbums.artist.artistName)

        removeSongFromPlaylistApi(songs)
    }

    suspend fun deletePlaylist(playlistWithSongs: PlaylistWithSongs) {
        deletePlaylistApi(playlistWithSongs)
    }

    suspend fun deleteAlbumsForArtist(
        artist: String,
        albums: List<AlbumWithSongs>
    ) {
        deleteAlbumsForArtistApi(artist, albums)

        val songs = mutableListOf<Song>()

        for (album in albums) {
            songs += album.songs
        }

        removeSongFromPlaylistApi(songs)
    }

    suspend fun deleteArtistForArtist(artist: ArtistWithAlbumsAndSongs) {
        deleteArtistForArtistApi(artist.name)

        val albums = getAlbumWithSongsForArtist(artist.name)

        val songs = mutableListOf<Song>()

        for (album in albums) {
            songs += album.songs
        }

        removeSongFromPlaylistApi(songs)
    }

    suspend fun deleteSongsForAlbum(songs: List<Song>) {
        deleteSongsForAlbumApi(songs)

        removeSongFromPlaylistApi(songs)
    }

    suspend fun deleteAlbumForAlbum(albumWithSongs: AlbumWithSongs) {
        deleteAlbumForAlbumApi(albumWithSongs)

        removeSongFromPlaylistApi(albumWithSongs.songs)
    }
}