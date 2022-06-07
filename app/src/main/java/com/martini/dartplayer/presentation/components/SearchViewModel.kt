package com.martini.dartplayer.presentation.components

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
): ViewModel() {

    private val _state = mutableStateOf(false)
    val state = _state

    init {
        listen()
    }

    private fun listen() {
        searchUseCase.listen
            .onEach {
                _state.value = it
            }.launchIn(viewModelScope)
    }

    fun set(value: Boolean) {
        searchUseCase(value).flowOn(Dispatchers.Default).launchIn(viewModelScope)
    }

}