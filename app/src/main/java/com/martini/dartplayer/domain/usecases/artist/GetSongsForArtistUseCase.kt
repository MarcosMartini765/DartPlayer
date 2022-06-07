package com.martini.dartplayer.domain.usecases.artist

import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSongsForArtistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableSharedFlow<GetSongsForArtistState>(0)

    operator fun invoke(artist: String) = flow {
        try {
            listen.emit(GetSongsForArtistState.Loading)
            emit(GetSongsForArtistState.Loading)
            val songs = musicRepository.getSongsForArtist(artist)
            listen.emit(GetSongsForArtistState.Loaded(songs))
            emit(GetSongsForArtistState.Loaded(songs))
        } catch (e: Exception) {
            listen.emit(GetSongsForArtistState.Failure)
            emit(GetSongsForArtistState.Failure)
        }
    }
}

sealed class GetSongsForArtistState {

    object Loading : GetSongsForArtistState()

    class Loaded(
        val albumWithSongs: List<AlbumWithSongs>
    ) : GetSongsForArtistState()

    object Failure : GetSongsForArtistState()
}
