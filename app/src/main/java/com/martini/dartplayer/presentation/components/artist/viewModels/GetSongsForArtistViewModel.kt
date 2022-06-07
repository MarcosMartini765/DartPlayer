package com.martini.dartplayer.presentation.components.artist.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.params.player.StartNewSessionParams
import com.martini.dartplayer.domain.usecases.playerService.StartNewSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PlayArtistViewModel @Inject constructor(
    private val startNewSessionUseCase: StartNewSessionUseCase
) : ViewModel() {
    operator fun invoke(params: StartNewSessionParams) {
        startNewSessionUseCase(params)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}