package com.martini.dartplayer.domain.usecases.album

import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAlbumForAlbumUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<DeleteAlbumForAlbumState>(DeleteAlbumForAlbumState.Initial)

    operator fun invoke(album: AlbumWithSongs): Flow<DeleteAlbumForAlbumState> = flow {
        try {
            emit(DeleteAlbumForAlbumState.Loading)
            listen.emit(DeleteAlbumForAlbumState.Loading)
            musicRepository.deleteAlbumForAlbum(album)
            emit(DeleteAlbumForAlbumState.Loaded)
            listen.emit(DeleteAlbumForAlbumState.Loaded)
        } catch (e: Exception) {
            emit(DeleteAlbumForAlbumState.Failure)
            listen.emit(DeleteAlbumForAlbumState.Failure)
        }
    }
}

sealed class DeleteAlbumForAlbumState {
    object Initial : DeleteAlbumForAlbumState()
    object Loading : DeleteAlbumForAlbumState()
    object Loaded : DeleteAlbumForAlbumState()
    object Failure : DeleteAlbumForAlbumState()
}