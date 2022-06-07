package com.martini.dartplayer.domain.usecases.artist

import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetArtistSortOrderUseCase @Inject constructor(
    private val sortOrderRepository: SortOrderRepository
) {
    operator fun invoke(order: SortOrder) = flow {
        try {
            emit(SetArtistSortOrderState.Loading)
            sortOrderRepository.setArtistSortOrder(order)
            emit(SetArtistSortOrderState.Loaded)
        } catch (e: Exception) {
            emit(SetArtistSortOrderState.Failure)
        }
    }
}

sealed class SetArtistSortOrderState {
    object Loading: SetArtistSortOrderState()
    object Loaded: SetArtistSortOrderState()
    object Failure: SetArtistSortOrderState()
}