package com.martini.dartplayer.presentation.components.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.usecases.DeleteMusicState
import com.martini.dartplayer.domain.usecases.DeleteMusicUseCase
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeleteMusicViewModel @Inject constructor(
    private val deleteMusicUseCase: DeleteMusicUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
    private val selected: SelectedMusicUseCase
) : ViewModel() {

    private val _state = mutableStateOf<DeleteMusicState>(DeleteMusicState.Initial)

    val state: State<DeleteMusicState> = _state

    init {
        listen()
    }

    operator fun invoke(selected: Selected) {
        deleteMusicUseCase(selected)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        deleteMusicUseCase.listen
            .onEach {
                _state.value = it
                if (it is DeleteMusicState.Loaded) {
                    loadSongsUseCase.loadSilently().flowOn(Dispatchers.IO)
                        .launchIn(viewModelScope)
                    selected.clear().flowOn(Dispatchers.Default)
                        .launchIn(viewModelScope)
                }
            }
            .launchIn(viewModelScope)
    }
}