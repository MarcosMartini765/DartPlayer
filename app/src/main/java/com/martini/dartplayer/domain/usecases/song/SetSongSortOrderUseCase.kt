package com.martini.dartplayer.domain.usecases.song

import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetSongSortOrderUseCase @Inject constructor(
    private val sortOrderRepository: SortOrderRepository
) {
    operator fun invoke(order: SortOrder) = flow {
        try {
            emit(SetSongSortOrderState.Loading)
            sortOrderRepository.setSongSortOrder(order)
            emit(SetSongSortOrderState.Loaded)
        } catch (e: Exception) {
            emit(SetSongSortOrderState.Failure)
        }
    }
}

sealed class SetSongSortOrderState {
    object Loading: SetSongSortOrderState()
    object Loaded: SetSongSortOrderState()
    object Failure: SetSongSortOrderState()
}