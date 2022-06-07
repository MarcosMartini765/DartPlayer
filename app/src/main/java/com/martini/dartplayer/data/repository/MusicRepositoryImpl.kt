package com.martini.dartplayer.data.repository

import android.content.Intent
import android.net.Uri
import com.martini.dartplayer.data.local.CachedPlaylistApi
import com.martini.dartplayer.data.local.LoadMusicApi
import com.martini.dartplayer.data.local.LocalMusicApi
import com.martini.dartplayer.data.model.SongResource
import com.martini.dartplayer.domain.entity.Music
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.entity.cachedSongs.CachedPlaybackParameters
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.albumForArtist.DeleteAlbumForArtistParams
import com.martini.dartplayer.domain.params.albumForArtist.LoadAlbumForArtistParams
import com.martini.dartplayer.domain.params.player.GetCachedPlaylistParams
import com.martini.dartplayer.domain.params.player.SetCachedPlaylistParams
import com.martini.dartplayer.domain.repository.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val scanMusicApi: LocalMusicApi,
    private val loadMusicApi: LoadMusicApi,
    private val cachedPlaylistApi: CachedPlaylistApi
) : MusicRepository {
    override suspend fun getMusic(): List<SongResource> {
        return scanMusicApi.getMusic()
    }

    override suspend fun loadMusic(): Music {
        return loadMusicApi.loadMusic()
    }

    override suspend fun search(text: String): Music {
        return loadMusicApi.search(text)
    }

    override suspend fun getAlbum(album: String): AlbumWithSongAndArtists {
        return loadMusicApi.loadAlbum(album)
    }

    override suspend fun getArtist(artist: String): ArtistWithAlbumsAndSongs {
        return loadMusicApi.loadArtist(artist)
    }

    override suspend fun getSongInfo(song: Song): Song {
        return loadMusicApi.getSongInfo(song)
    }

    override suspend fun getAlbumInfo(album: AlbumWithSongs): AlbumWithSongAndArtists {
        return loadMusicApi.getAlbumInfo(album)
    }

    override suspend fun getArtistInfo(artist: ArtistWithAlbums): ArtistWithAlbums {
        return loadMusicApi.getArtistInfo(artist)
    }

    override suspend fun getPlaylistInfo(playlist: PlaylistWithSongs): PlaylistWithSongs {
        return loadMusicApi.getPlaylistInfo(playlist)
    }

    override suspend fun deleteMusic(selected: Selected) {
        return scanMusicApi.deleteMusic(selected)
    }

    override suspend fun deleteAlbumsForArtist(
        artist: ArtistWithAlbumsAndSongs,
        album: List<AlbumWithSongs>
    ) {
        return scanMusicApi.deleteAlbumsForArtist(artist, album)
    }

    override suspend fun deleteArtistForArtist(artist: ArtistWithAlbumsAndSongs) {
        return scanMusicApi.deleteArtistForArtist(artist)
    }

    override suspend fun deleteSongsForAlbum(songs: List<Song>) {
        return scanMusicApi.deleteSongsForAlbum(songs)
    }

    override suspend fun deleteAlbumForAlbum(album: AlbumWithSongs) {
        return scanMusicApi.deleteAlbumForAlbum(album)
    }

    override suspend fun loadAlbumForArtist(loadAlbumForArtistParams: LoadAlbumForArtistParams): AlbumWithSongs {
        return loadMusicApi.loadAlbumForArtist(loadAlbumForArtistParams)
    }

    override suspend fun deleteAlbumForArtist(deleteAlbumForArtistParams: DeleteAlbumForArtistParams) {
        return scanMusicApi.deleteAlbumForArtist(deleteAlbumForArtistParams)
    }

    override suspend fun getSongForId(id: Long): Song {
        return loadMusicApi.getSongForId(id)
    }

    override suspend fun getSongsForArtist(artist: String): List<AlbumWithSongs> {
        return loadMusicApi.getSongsForArtist(artist)
    }

    override suspend fun getListOfUris(selected: Selected): List<Uri> {
        return loadMusicApi.getListOfUris(selected)
    }

    override suspend fun clearCachedPlaylist() {
        return cachedPlaylistApi.clear()
    }

    override suspend fun setCachedPlaylist(params: SetCachedPlaylistParams) {
        return cachedPlaylistApi.insertPlaylist(params)
    }

    override suspend fun getCachedPlaylist(): GetCachedPlaylistParams {
        return cachedPlaylistApi.getPlaylist()
    }

    override suspend fun updateCachedPlaylistParams(params: CachedPlaybackParameters) {
        return cachedPlaylistApi.updatePlaybackParameters(params)
    }

    override suspend fun getCachedPlaylistParams(): CachedPlaybackParameters {
        return cachedPlaylistApi.getParams()
    }

    override suspend fun getSongForUri(intent: Intent): Song {
        return scanMusicApi.getSongForUri(intent)
    }
}