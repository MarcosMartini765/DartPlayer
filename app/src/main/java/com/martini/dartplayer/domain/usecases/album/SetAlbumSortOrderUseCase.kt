package com.martini.dartplayer.domain.usecases.album

import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetAlbumSortOrderUseCase @Inject constructor(
    private val sortOrderRepository: SortOrderRepository
) {
    operator fun invoke(order: SortOrder) = flow {
        try {
            emit(SetAlbumSortOrderState.Loading)
            sortOrderRepository.setAlbumSortOrder(order)
            emit(SetAlbumSortOrderState.Loaded)
        } catch (e: Exception) {
            emit(SetAlbumSortOrderState.Failure)
        }
    }
}

sealed class SetAlbumSortOrderState {
    object Loading: SetAlbumSortOrderState()
    object Loaded: SetAlbumSortOrderState()
    object Failure: SetAlbumSortOrderState()
}