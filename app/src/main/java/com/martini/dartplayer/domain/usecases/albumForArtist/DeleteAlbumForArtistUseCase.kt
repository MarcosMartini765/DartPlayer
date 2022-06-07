package com.martini.dartplayer.domain.usecases.albumForArtist

import com.martini.dartplayer.domain.params.albumForArtist.DeleteAlbumForArtistParams
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAlbumForArtistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<DeleteAlbumForArtistState>(DeleteAlbumForArtistState.Initial)

    operator fun invoke(
        deleteAlbumForArtistParams: DeleteAlbumForArtistParams
    ) = flow {
        try {
            emit(DeleteAlbumForArtistState.Loading)
            listen.emit(DeleteAlbumForArtistState.Loading)
            musicRepository.deleteAlbumForArtist(deleteAlbumForArtistParams)
            emit(DeleteAlbumForArtistState.Loaded)
            listen.emit(DeleteAlbumForArtistState.Loaded)
        } catch (e: Exception) {
            emit(DeleteAlbumForArtistState.Failure)
            listen.emit(DeleteAlbumForArtistState.Failure)
        }
    }

}

sealed class DeleteAlbumForArtistState {
    object Initial : DeleteAlbumForArtistState()
    object Loading : DeleteAlbumForArtistState()
    object Loaded : DeleteAlbumForArtistState()
    object Failure : DeleteAlbumForArtistState()
}