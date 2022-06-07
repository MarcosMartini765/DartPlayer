package com.martini.dartplayer.domain.usecases.files

import com.martini.dartplayer.domain.entity.files.LocalFilesSettings
import com.martini.dartplayer.domain.repository.LocalFilesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InitializePreferencesUseCase @Inject constructor(
    private val localFilesRepository: LocalFilesRepository
) {
    operator fun invoke(): Flow<InitializePreferencesState> = flow {
        try {
            emit(InitializePreferencesState.Loading)
            val settings = localFilesRepository.initializeSettings()
            emit(InitializePreferencesState.Loaded(settings))
        } catch (e: Exception) {
            emit(InitializePreferencesState.Failure)
        }
    }
}


sealed class InitializePreferencesState {

    object Loading : InitializePreferencesState()

    class Loaded(
        val settings: LocalFilesSettings
    ) : InitializePreferencesState()

    object Failure : InitializePreferencesState()
}