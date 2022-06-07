package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PageStateViewModel @Inject constructor(

) : ViewModel() {
    private val _state = mutableStateOf(0)

    val state: State<Int> = _state

    operator fun invoke(page: Int) {
        _state.value = page
    }
}