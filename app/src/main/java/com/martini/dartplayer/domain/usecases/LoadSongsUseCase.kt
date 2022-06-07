package com.martini.dartplayer.domain.usecases

import com.martini.dartplayer.domain.entity.Music
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadSongsUseCase @Inject constructor(
    private val repository: MusicRepository
) {

    val listen = MutableStateFlow<LoadSongsState>(LoadSongsState.Loading)

    operator fun invoke(): Flow<LoadSongsState> = flow {
        try {
            emit(LoadSongsState.Loading)
            listen.emit(LoadSongsState.Loading)
            val music = repository.loadMusic()
            emit(LoadSongsState.Loaded(music))
            listen.emit(LoadSongsState.Loaded(music))
        } catch (e: Exception) {
            emit(LoadSongsState.Failure)
            listen.emit(LoadSongsState.Failure)
        }
    }

    fun loadSilently(): Flow<LoadSongsState> = flow {
        try {
            val music = repository.loadMusic()
            emit(LoadSongsState.Loaded(music))
            listen.emit(LoadSongsState.Loaded(music))
        } catch (e: Exception) {
            emit(LoadSongsState.Failure)
            listen.emit(LoadSongsState.Failure)
        }
    }

    fun search(text: String): Flow<LoadSongsState> = flow {
        try {
            val music = repository.search(text)
            emit(LoadSongsState.Loaded(music))
            listen.emit(LoadSongsState.Loaded(music))
        } catch (e: Exception) {
            emit(LoadSongsState.Failure)
            listen.emit(LoadSongsState.Failure)
        }
    }
}

sealed class LoadSongsState {
    object Initial : LoadSongsState()
    object Loading : LoadSongsState()
    class Loaded(val music: Music) : LoadSongsState()
    object Failure : LoadSongsState()
}