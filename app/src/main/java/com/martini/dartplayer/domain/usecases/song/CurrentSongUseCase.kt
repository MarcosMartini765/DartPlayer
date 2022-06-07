package com.martini.dartplayer.domain.usecases.song

import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrentSongUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    val listen = MutableStateFlow<CurrentSongState>(CurrentSongState.Initial)

    operator fun invoke(id: Long) = flow {
        try {
            val song = musicRepository.getSongForId(id)
            listen.emit(CurrentSongState.Loaded(song))
            emit(CurrentSongState.Loaded(song))
        } catch (e: Exception) {
            listen.emit(CurrentSongState.Failure)
            emit(CurrentSongState.Failure)
        }
    }
}

sealed class CurrentSongState {
    object Initial : CurrentSongState()

    object Loading : CurrentSongState()

    class Loaded(
        val song: Song
    ) : CurrentSongState()

    object Failure : CurrentSongState()
}