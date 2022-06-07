package com.martini.dartplayer.domain.usecases

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.martini.dartplayer.data.model.toSong
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScanSongsUseCase @Inject constructor(
    private val repository: MusicRepository,
    private val crashlytics: FirebaseCrashlytics
) {

    val listen = MutableSharedFlow<ScanSongsState>(0)

    operator fun invoke(): Flow<ScanSongsState> = flow {
        try {
            emit(ScanSongsState.Loading)
            listen.emit(ScanSongsState.Loading)
            val songs = repository.getMusic().map { it.toSong() }
            emit(ScanSongsState.Loaded(songs))
            listen.emit(ScanSongsState.Loaded(songs))
        } catch (e: Exception) {
            crashlytics.recordException(e)
            emit(ScanSongsState.Failure)
            listen.emit(ScanSongsState.Failure)
        }
    }
}

sealed class ScanSongsState {
    object Initial : ScanSongsState()

    object Loading : ScanSongsState()

    class Loaded(
        val songs: List<Song>
    ) : ScanSongsState()

    object Failure : ScanSongsState()
}