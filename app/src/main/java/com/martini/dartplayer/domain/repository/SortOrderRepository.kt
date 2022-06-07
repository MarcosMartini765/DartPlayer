package com.martini.dartplayer.domain.repository

import com.martini.dartplayer.domain.entity.song.SortOrder

interface SortOrderRepository {
    fun getSongSortOrder(): SortOrder
    fun getAlbumSortOrder(): SortOrder
    fun getArtistSortOrder(): SortOrder
    fun getPlaylistSortOrder(): SortOrder

    fun setSongSortOrder(order: SortOrder)
    fun setAlbumSortOrder(order: SortOrder)
    fun setArtistSortOrder(order: SortOrder)
    fun setPlaylistSortOrder(order: SortOrder)
}