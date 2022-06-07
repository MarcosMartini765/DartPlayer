package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.domain.params.player.StartNewSessionParams
import com.martini.dartplayer.domain.usecases.playerService.StartNewSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PlaySongsAtIndexViewModel @Inject constructor(
    private val startNewSessionUseCase: StartNewSessionUseCase
) : ViewModel() {
    operator fun invoke(params: PlaySongAtIndexParams) {
        startNewSessionUseCase(
            StartNewSessionParams(
                selected = Selected(params.songs),
                shuffled = false,
                index = params.index
            )
        ).flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}
