package com.martini.dartplayer.domain.usecases

import android.content.Intent
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayRequestedSongUseCase @Inject constructor(
    private val repository: MusicRepository
) {
    val listen = MutableSharedFlow<PlayRequestedSongState>(0)

    operator fun invoke(intent: Intent) = flow {
        try {
            emit(PlayRequestedSongState.Loading)
            listen.emit(PlayRequestedSongState.Loading)
            val song = repository.getSongForUri(intent)
            emit(PlayRequestedSongState.Loaded(song))
            listen.emit(PlayRequestedSongState.Loaded(song))
        } catch (e: Exception) {
            emit(PlayRequestedSongState.Failure)
            listen.emit(PlayRequestedSongState.Failure)
        }
    }

}

sealed class PlayRequestedSongState {
    object Loading : PlayRequestedSongState()
    class Loaded(
        val song: Song
    ) : PlayRequestedSongState()

    object Failure : PlayRequestedSongState()
}