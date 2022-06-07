package com.martini.dartplayer.domain.usecases

import android.net.Uri
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListOfUrisUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableSharedFlow<GetListOfUrisState>(0)

    operator fun invoke(selected: Selected) = flow {
        try {
            listen.emit(GetListOfUrisState.Loading)
            emit(GetListOfUrisState.Loading)
            val uris = musicRepository.getListOfUris(selected)
            listen.emit(GetListOfUrisState.Loaded(uris))
            emit(GetListOfUrisState.Loaded(uris))
        } catch (e: Exception) {
            listen.emit(GetListOfUrisState.Failure)
            emit(GetListOfUrisState.Failure)
        }
    }
}

sealed class GetListOfUrisState {
    object Loading : GetListOfUrisState()
    class Loaded(
        val uris: List<Uri>
    ) : GetListOfUrisState()

    object Failure : GetListOfUrisState()
}