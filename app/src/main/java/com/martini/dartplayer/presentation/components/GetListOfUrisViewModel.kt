package com.martini.dartplayer.presentation.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.domain.usecases.GetListOfUrisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetListOfUrisViewModel @Inject constructor(
    private val getListOfUrisUseCase: GetListOfUrisUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetListOfUrisState>(GetListOfUrisState.Loading)

    val state: State<GetListOfUrisState> = _state

    init {
        listen()
    }

    private fun listen() {
        getListOfUrisUseCase.listen
            .onEach {
                _state.value = it
            }
            .launchIn(viewModelScope)
    }

    operator fun invoke(selected: Selected): Flow<GetListOfUrisState> {
        return getListOfUrisUseCase(selected)
    }
}