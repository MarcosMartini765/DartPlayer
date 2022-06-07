package com.martini.dartplayer.domain.repository

import android.content.Intent
import android.net.Uri
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

interface MusicRepository {

    suspend fun getMusic(): List<SongResource>
    suspend fun loadMusic(): Music
    suspend fun search(text: String): Music

    suspend fun getAlbum(album: String): AlbumWithSongAndArtists
    suspend fun getArtist(artist: String): ArtistWithAlbumsAndSongs

    suspend fun getSongInfo(song: Song): Song
    suspend fun getAlbumInfo(album: AlbumWithSongs): AlbumWithSongAndArtists
    suspend fun getArtistInfo(artist: ArtistWithAlbums): ArtistWithAlbums
    suspend fun getPlaylistInfo(playlist: PlaylistWithSongs): PlaylistWithSongs

    suspend fun deleteMusic(selected: Selected)

    suspend fun deleteAlbumsForArtist(
        artist: ArtistWithAlbumsAndSongs,
        album: List<AlbumWithSongs>
    )

    suspend fun deleteArtistForArtist(artist: ArtistWithAlbumsAndSongs)

    suspend fun deleteSongsForAlbum(songs: List<Song>)
    suspend fun deleteAlbumForAlbum(album: AlbumWithSongs)

    suspend fun loadAlbumForArtist(loadAlbumForArtistParams: LoadAlbumForArtistParams): AlbumWithSongs
    suspend fun deleteAlbumForArtist(deleteAlbumForArtistParams: DeleteAlbumForArtistParams)

    suspend fun getSongForId(id: Long): Song

    suspend fun getSongsForArtist(artist: String): List<AlbumWithSongs>

    suspend fun getListOfUris(selected: Selected): List<Uri>

    suspend fun clearCachedPlaylist()
    suspend fun setCachedPlaylist(params: SetCachedPlaylistParams)
    suspend fun getCachedPlaylist(): GetCachedPlaylistParams
    suspend fun updateCachedPlaylistParams(params: CachedPlaybackParameters)
    suspend fun getCachedPlaylistParams(): CachedPlaybackParameters

    suspend fun getSongForUri(intent: Intent): Song
}