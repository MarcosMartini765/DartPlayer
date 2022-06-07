package com.martini.dartplayer.presentation.components.settings.local

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.dispatchers.ScanMusicDispatcher
import com.martini.dartplayer.domain.usecases.ScanSongsState
import com.martini.dartplayer.domain.usecases.ScanSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ScanMusicViewModel @Inject constructor(
    private val scanSongsUseCase: ScanSongsUseCase,
    private val scanMusicDispatcher: ScanMusicDispatcher
) : ViewModel() {

    private val _state = mutableStateOf<ScanSongsState>(ScanSongsState.Initial)
    val state: State<ScanSongsState> = _state

    init {
        listen()
    }

    fun scanSongs() {
        scanMusicDispatcher.scan()
            .launchIn(viewModelScope)
    }

    private fun listen() {
        scanSongsUseCase.listen.onEach {
            _state.value = it
        }.launchIn(viewModelScope)
    }
}