package com.martini.dartplayer.domain.usecases.song

import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSongInfoUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<GetSongInfoState>(GetSongInfoState.Initial)

    operator fun invoke(song: Song): Flow<GetSongInfoState> = flow {
        try {
            emit(GetSongInfoState.Loading)
            listen.emit(GetSongInfoState.Loading)
            val info = musicRepository.getSongInfo(song)
            emit(GetSongInfoState.LoadedSong(info))
            listen.emit(GetSongInfoState.LoadedSong(info))
        } catch (e: Exception) {
            emit(GetSongInfoState.Failure)
            listen.emit(GetSongInfoState.Failure)
        }
    }

    fun getArtistInfo(artistWithAlbums: ArtistWithAlbums): Flow<GetSongInfoState> = flow {
        try {
            emit(GetSongInfoState.Loading)
            listen.emit(GetSongInfoState.Loading)
            val info = musicRepository.getArtistInfo(artistWithAlbums)
            emit(GetSongInfoState.LoadedArtist(info))
            listen.emit(GetSongInfoState.LoadedArtist(info))
        } catch (e: Exception) {
            emit(GetSongInfoState.Failure)
            listen.emit(GetSongInfoState.Failure)
        }
    }

    fun getAlbumInfo(album: AlbumWithSongs): Flow<GetSongInfoState> = flow {
        try {
            emit(GetSongInfoState.Loading)
            listen.emit(GetSongInfoState.Loading)
            val info = musicRepository.getAlbumInfo(album)
            emit(GetSongInfoState.LoadedAlbum(info))
            listen.emit(GetSongInfoState.LoadedAlbum(info))
        } catch (e: Exception) {
            emit(GetSongInfoState.Failure)
            listen.emit(GetSongInfoState.Failure)
        }
    }

    fun getPlaylistInfo(playlistWithSongs: PlaylistWithSongs): Flow<GetSongInfoState> = flow {
        try {
            emit(GetSongInfoState.Loading)
            listen.emit(GetSongInfoState.Loading)
            val info = musicRepository.getPlaylistInfo(playlistWithSongs)
            emit(GetSongInfoState.LoadedPlaylist(info))
            listen.emit(GetSongInfoState.LoadedPlaylist(info))
        } catch (e: Exception) {
            emit(GetSongInfoState.Failure)
            listen.emit(GetSongInfoState.Failure)
        }
    }

}

sealed class GetSongInfoState {
    object Initial : GetSongInfoState()
    object Loading : GetSongInfoState()

    class LoadedSong(
        val song: Song
    ) : GetSongInfoState()

    class LoadedArtist(
        val artistWithAlbums: ArtistWithAlbums
    ) : GetSongInfoState()

    class LoadedAlbum(
        val album: AlbumWithSongAndArtists
    ) : GetSongInfoState()

    class LoadedPlaylist(
        val playlistWithSongs: PlaylistWithSongs
    ) : GetSongInfoState()

    object Failure : GetSongInfoState()
}