package com.martini.dartplayer.data.local

import android.content.Context
import android.net.Uri
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.Music
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.params.albumForArtist.LoadAlbumForArtistParams
import java.util.*

class LoadMusicApi(
    private val musicDao: MusicDao,
    private val playlistDao: PlaylistDao,
    context: Context,
) {
    private val orderPrefs =
        context.getSharedPreferences(Constants.SORT_ORDER_PREF, Context.MODE_PRIVATE)

    private fun getSongSortOrder(): SortOrder {
        val order = orderPrefs.getString(Constants.SORT_ORDER_SONG, SortOrder.Asc.name)
            ?: return SortOrder.Asc

        return try {
            SortOrder.valueOf(order)
        } catch (e: Exception) {
            SortOrder.Asc
        }

    }

    private fun getAlbumSortOrder(): SortOrder {
        val order = orderPrefs.getString(Constants.SORT_ORDER_ALBUM, SortOrder.Asc.name)
            ?: return SortOrder.Asc

        return try {
            SortOrder.valueOf(order)
        } catch (e: Exception) {
            SortOrder.Asc
        }

    }

    private fun getArtistSortOrder(): SortOrder {
        val order = orderPrefs.getString(Constants.SORT_ORDER_ARTIST, SortOrder.Asc.name)
            ?: return SortOrder.Asc

        return try {
            SortOrder.valueOf(order)
        } catch (e: Exception) {
            SortOrder.Asc
        }

    }

    private fun getPlaylistSortOrder(): SortOrder {
        val order = orderPrefs.getString(Constants.SORT_ORDER_PLAYLIST, SortOrder.Asc.name)
            ?: return SortOrder.Asc

        return try {
            SortOrder.valueOf(order)
        } catch (e: Exception) {
            SortOrder.Asc
        }

    }

    suspend fun loadMusic(): Music {
        return Music(
            songs = when (getSongSortOrder()) {
                SortOrder.Asc -> musicDao.getAllSongs()
                SortOrder.Desc -> musicDao.getAllSongsDesc()
                SortOrder.DateAdded -> musicDao.getAllSongsDateAdd()
            },
            albums = when (getAlbumSortOrder()) {
                SortOrder.Asc -> musicDao.getAlbumsWithSongs()
                SortOrder.Desc -> musicDao.getAlbumsWithSongsDesc()
                else -> musicDao.getAlbumsWithSongs()
            },
            artists = when (getArtistSortOrder()) {
                SortOrder.Asc -> musicDao.getArtistsWithAlbums()
                SortOrder.Desc -> musicDao.getArtistsWithAlbumsDesc()
                else -> musicDao.getArtistsWithAlbums()
            },
            playlists = when (getPlaylistSortOrder()) {
                SortOrder.Asc -> playlistDao.getAllPlaylists()
                SortOrder.Desc -> playlistDao.getAllPlaylistsDesc()
                else -> playlistDao.getAllPlaylists()
            }
        )
    }

    suspend fun loadAlbum(album: String): AlbumWithSongAndArtists {

        val raw = musicDao.getAlbumWithSongs(album)

        return AlbumWithSongAndArtists(
            songs = AlbumWithSongs(
                album = raw.album,
                songs = raw.songs.sortedBy { it.name }
            ),
            artists = musicDao.getAlbumWithArtists(album)
        )
    }

    suspend fun loadArtist(artist: String): ArtistWithAlbumsAndSongs {

        val albums = musicDao.getArtistWithAlbums(artist).albums.sortedBy { it.albumName }

        val albumsWithSongs = mutableListOf<AlbumWithSongs>()

        albums.forEach {
            albumsWithSongs.add(
                AlbumWithSongs(
                    album = it,
                    songs = musicDao.getSongsForAlbumAndArtist(it.albumName, artist)
                        .sortedBy { song -> song.name }
                )
            )
        }

        return ArtistWithAlbumsAndSongs(
            name = artist,
            albums = albumsWithSongs,
        )
    }

    fun getSongInfo(song: Song): Song {
        return song
    }

    fun getArtistInfo(artist: ArtistWithAlbums): ArtistWithAlbums {
        return artist
    }

    suspend fun getAlbumInfo(albumWithSongs: AlbumWithSongs): AlbumWithSongAndArtists {
        return AlbumWithSongAndArtists(
            songs = albumWithSongs,
            artists = musicDao.getAlbumWithArtists(albumWithSongs.album.albumName)
        )
    }

    fun getPlaylistInfo(playlistWithSongs: PlaylistWithSongs) = playlistWithSongs

    suspend fun search(input: String): Music {
        val text = input.lowercase(Locale.getDefault())

        val songs = musicDao.searchSongs(text)

        val albums = musicDao.searchAlbums(text)
        val artists = musicDao.searchArtists(text)

        return Music(
            songs = songs,
            albums = albums,
            artists = artists,
            playlists = playlistDao.getAllPlaylists()
        )
    }

    suspend fun loadAlbumForArtist(loadAlbumForArtistParams: LoadAlbumForArtistParams): AlbumWithSongs {
        return AlbumWithSongs(
            album = musicDao.getAlbum(loadAlbumForArtistParams.albumName),
            songs = musicDao.getSongsForAlbumAndArtist(
                album = loadAlbumForArtistParams.albumName,
                artist = loadAlbumForArtistParams.artistName
            ).sortedBy { it.name }
        )
    }

    suspend fun getSongForId(id: Long): Song {
        return musicDao.getSongForId(id)
    }

    suspend fun getSongsForArtist(artist: String): List<AlbumWithSongs> {

        val sortedAlbums = mutableListOf<AlbumWithSongs>()

        loadArtist(artist).albums.sortedBy { it.album.albumName }.forEach {
            sortedAlbums.add(
                AlbumWithSongs(
                    album = it.album,
                    songs = it.songs.sortedBy { song -> song.name }
                )
            )
        }
        return sortedAlbums
    }

    suspend fun getListOfUris(selected: Selected): MutableList<Uri> {
        val uris = mutableListOf<Uri>()

        for (entity in selected.music) {
            when (entity) {
                is Song -> {
                    uris.add(Uri.parse(entity.uri))
                }
                is AlbumWithSongs -> {
                    uris.addAll(entity.songs.map { Uri.parse(it.uri) })
                }
                is ArtistWithAlbums -> {
                    entity.albums.forEach {
                        musicDao.getAlbumWithSongs(it.albumName).songs.also { songs ->
                            uris.addAll(songs.map { song -> Uri.parse(song.uri) })
                        }
                    }
                }
            }
        }
        return uris
    }
}