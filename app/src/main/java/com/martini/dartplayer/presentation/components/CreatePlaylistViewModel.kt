package com.martini.dartplayer.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.CreatePlaylistState
import com.martini.dartplayer.domain.usecases.CreatePlaylistUseCase
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreatePlaylistViewModel @Inject constructor(
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val loadSongsUseCase: LoadSongsUseCase
) : ViewModel() {
    operator fun invoke(name: String) {
        createPlaylistUseCase(name)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is CreatePlaylistState.Loaded) {
                    loadMusic()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadMusic() {
        loadSongsUseCase.loadSilently()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}