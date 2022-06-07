package com.martini.dartplayer.domain.usecases

import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteMusicUseCase @Inject constructor(
    val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<DeleteMusicState>(DeleteMusicState.Initial)

    operator fun invoke(selected: Selected): Flow<DeleteMusicState> = flow {
        try {
            emit(DeleteMusicState.Loading)
            listen.emit(DeleteMusicState.Loading)
            musicRepository.deleteMusic(selected)
            emit(DeleteMusicState.Loaded)
            listen.emit(DeleteMusicState.Loaded)
        } catch (e: Exception) {
            emit(DeleteMusicState.Failure)
            listen.emit(DeleteMusicState.Failure)
        }
    }
}


sealed class DeleteMusicState {
    object Initial : DeleteMusicState()
    object Loading : DeleteMusicState()
    object Loaded : DeleteMusicState()
    object Failure : DeleteMusicState()
}