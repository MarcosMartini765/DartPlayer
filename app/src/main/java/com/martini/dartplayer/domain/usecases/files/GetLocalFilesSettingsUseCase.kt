package com.martini.dartplayer.domain.usecases.files

import com.martini.dartplayer.domain.entity.files.LocalFilesSettings
import com.martini.dartplayer.domain.repository.LocalFilesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocalFilesSettingsUseCase @Inject constructor(
    private val localFilesRepository: LocalFilesRepository
) {

    val listen = MutableStateFlow<GetLocalFilesState>(GetLocalFilesState.Initial)

    operator fun invoke(): Flow<GetLocalFilesState> = flow {
        try {
            emit(GetLocalFilesState.Loading)
            listen.emit(GetLocalFilesState.Loading)
            val settings = localFilesRepository.getSettings()
            emit(GetLocalFilesState.Loaded(settings))
            listen.emit(GetLocalFilesState.Loaded(settings))
        } catch (e: Exception) {
            emit(GetLocalFilesState.Failure)
            listen.emit(GetLocalFilesState.Failure)
        }
    }
}


sealed class GetLocalFilesState {

    object Initial : GetLocalFilesState()

    object Loading : GetLocalFilesState()

    class Loaded(
        val settings: LocalFilesSettings
    ) : GetLocalFilesState()

    object Failure : GetLocalFilesState()
}
