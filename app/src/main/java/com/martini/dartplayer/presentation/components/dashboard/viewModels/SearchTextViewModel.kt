package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchTextViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf("")

    val state: State<String> = _state

    operator fun invoke(newText: String) {
        _state.value = newText
    }

}