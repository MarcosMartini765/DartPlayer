package com.martini.dartplayer.domain.usecases.files

import com.martini.dartplayer.domain.entity.files.LocalFilesSettings
import com.martini.dartplayer.domain.repository.LocalFilesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateLocalFilesSettingsUseCase @Inject constructor(
    private val localFilesRepository: LocalFilesRepository
) {
    operator fun invoke(
        newSettings: LocalFilesSettings
    ): Flow<UpdateLocalFilesState> = flow {
        try {
            emit(UpdateLocalFilesState.Loading)
            val settings = localFilesRepository.updateSettings(newSettings)
            emit(UpdateLocalFilesState.Loaded(settings))
        } catch (e: Exception) {
            emit(UpdateLocalFilesState.Failure)
        }
    }
}


sealed class UpdateLocalFilesState {


    object Loading : UpdateLocalFilesState()

    class Loaded(
        val settings: LocalFilesSettings
    ) : UpdateLocalFilesState()

    object Failure : UpdateLocalFilesState()
}