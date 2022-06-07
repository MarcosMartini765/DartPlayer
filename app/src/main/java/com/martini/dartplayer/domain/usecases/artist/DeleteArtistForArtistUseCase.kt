package com.martini.dartplayer.domain.usecases.artist

import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteArtistForArtistUseCase @Inject constructor(
    val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<DeleteArtistForArtistState>(DeleteArtistForArtistState.Initial)

    operator fun invoke(
        artist: ArtistWithAlbumsAndSongs,
    ): Flow<DeleteArtistForArtistState> = flow {
        try {
            emit(DeleteArtistForArtistState.Loading)
            listen.emit(DeleteArtistForArtistState.Loading)
            musicRepository.deleteArtistForArtist(artist)
            emit(DeleteArtistForArtistState.Loaded)
            listen.emit(DeleteArtistForArtistState.Loaded)
        } catch (e: Exception) {
            emit(DeleteArtistForArtistState.Failure)
            listen.emit(DeleteArtistForArtistState.Failure)
        }
    }
}

sealed class DeleteArtistForArtistState {
    object Initial : DeleteArtistForArtistState()
    object Loaded : DeleteArtistForArtistState()
    object Loading : DeleteArtistForArtistState()
    object Failure : DeleteArtistForArtistState()
}