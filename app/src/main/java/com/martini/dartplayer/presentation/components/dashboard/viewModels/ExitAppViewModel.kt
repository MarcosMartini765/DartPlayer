package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.ExitAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ExitAppViewModel @Inject constructor(
    private val exitAppUseCase: ExitAppUseCase
) : ViewModel() {
    operator fun invoke() {
        exitAppUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}