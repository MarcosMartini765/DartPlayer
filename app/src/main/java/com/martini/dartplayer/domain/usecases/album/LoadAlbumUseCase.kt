package com.martini.dartplayer.domain.usecases.album

import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadAlbumUseCase @Inject constructor(
    private val loadMusicRepository: MusicRepository
) {

    val listen = MutableStateFlow<LoadAlbumState>(LoadAlbumState.Initial)

    operator fun invoke(album: String): Flow<LoadAlbumState> = flow {
        try {
            emit(LoadAlbumState.Loading)
            listen.emit(LoadAlbumState.Loading)
            val albumData = loadMusicRepository.getAlbum(album)
            emit(LoadAlbumState.Loaded(albumData))
            listen.emit(LoadAlbumState.Loaded(albumData))
        } catch (e: Exception) {
            emit(LoadAlbumState.Failure)
            listen.emit(LoadAlbumState.Failure)
        }
    }
}


sealed class LoadAlbumState {
    object Initial : LoadAlbumState()

    object Loading : LoadAlbumState()

    class Loaded(
        val albumWithSongAndArtists: AlbumWithSongAndArtists
    ) : LoadAlbumState()

    object Failure : LoadAlbumState()
}