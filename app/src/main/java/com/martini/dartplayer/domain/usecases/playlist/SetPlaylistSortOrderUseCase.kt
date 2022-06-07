package com.martini.dartplayer.domain.usecases.playlist

import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetPlaylistSortOrderUseCase @Inject constructor(
    private val sortOrderRepository: SortOrderRepository
) {
    operator fun invoke(order: SortOrder) = flow {
        try {
            emit(SetPlaylistSortOrderState.Loading)
            sortOrderRepository.setPlaylistSortOrder(order)
            emit(SetPlaylistSortOrderState.Loaded)
        } catch (e: Exception) {
            emit(SetPlaylistSortOrderState.Failure)
        }
    }
}

sealed class SetPlaylistSortOrderState {
    object Loading: SetPlaylistSortOrderState()
    object Loaded: SetPlaylistSortOrderState()
    object Failure: SetPlaylistSortOrderState()
}