package com.martini.dartplayer.domain.usecases.albumForArtist

import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSongsForAlbumUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<DeleteSongsForAlbumState>(DeleteSongsForAlbumState.Initial)

    operator fun invoke(songs: List<Song>): Flow<DeleteSongsForAlbumState> = flow {
        try {
            emit(DeleteSongsForAlbumState.Loading)
            listen.emit(DeleteSongsForAlbumState.Loading)
            musicRepository.deleteSongsForAlbum(songs)
            emit(DeleteSongsForAlbumState.Loaded)
            listen.emit(DeleteSongsForAlbumState.Loaded)
        } catch (e: Exception) {
            emit(DeleteSongsForAlbumState.Failure)
            listen.emit(DeleteSongsForAlbumState.Failure)
        }
    }
}

sealed class DeleteSongsForAlbumState {
    object Initial : DeleteSongsForAlbumState()
    object Loading : DeleteSongsForAlbumState()
    object Loaded : DeleteSongsForAlbumState()
    object Failure : DeleteSongsForAlbumState()
}