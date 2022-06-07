package com.martini.dartplayer.domain.usecases.artist

import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadArtistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<LoadArtistState>(LoadArtistState.Initial)

    operator fun invoke(artist: String): Flow<LoadArtistState> = flow {
        try {
            emit(LoadArtistState.Loading)
            listen.emit(LoadArtistState.Loading)
            val data = musicRepository.getArtist(artist)
            emit(LoadArtistState.Loaded(data))
            listen.emit(LoadArtistState.Loaded(data))
        } catch (e: Exception) {
            emit(LoadArtistState.Failure)
            listen.emit(LoadArtistState.Failure)
        }
    }
}

sealed class LoadArtistState {

    object Initial : LoadArtistState()

    object Loading : LoadArtistState()

    class Loaded(
        val data: ArtistWithAlbumsAndSongs
    ) : LoadArtistState()

    object Failure : LoadArtistState()
}
