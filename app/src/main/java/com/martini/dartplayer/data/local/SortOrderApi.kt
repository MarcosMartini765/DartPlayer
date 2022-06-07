package com.martini.dartplayer.data.local

import android.content.Context
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.song.SortOrder
import javax.inject.Inject

class SortOrderApi @Inject constructor(
    context: Context
) {
    private val orderPrefs =
        context.getSharedPreferences(Constants.SORT_ORDER_PREF, Context.MODE_PRIVATE)

    fun getSongSortOrder(): SortOrder {
        val order = orderPrefs.getString(Constants.SORT_ORDER_SONG, SortOrder.Asc.name)
            ?: return SortOrder.Asc

        return try {
            SortOrder.valueOf(order)
        } catch (e: Exception) {
            SortOrder.Asc
        }
    }

    fun setSongSortOrder(order: SortOrder) {
        with(orderPrefs.edit()) {
            putString(Constants.SORT_ORDER_SONG, order.name)
            apply()
        }
    }

    fun getAlbumSortOrder(): SortOrder {
        val order = orderPrefs.getString(Constants.SORT_ORDER_ALBUM, SortOrder.Asc.name)
            ?: return SortOrder.Asc

        return try {
            SortOrder.valueOf(order)
        } catch (e: Exception) {
            SortOrder.Asc
        }
    }

    fun setAlbumSortOrder(order: SortOrder) {
        with(orderPrefs.edit()) {
            putString(Constants.SORT_ORDER_ALBUM, order.name)
            apply()
        }
    }

    fun getArtistSortOrder(): SortOrder {
        val order = orderPrefs.getString(Constants.SORT_ORDER_ARTIST, SortOrder.Asc.name)
            ?: return SortOrder.Asc

        return try {
            SortOrder.valueOf(order)
        } catch (e: Exception) {
            SortOrder.Asc
        }
    }

    fun setArtistSortOrder(order: SortOrder) {
        with(orderPrefs.edit()) {
            putString(Constants.SORT_ORDER_ARTIST, order.name)
            apply()
        }
    }

    fun getPlaylistSortOrder(): SortOrder {
        val order = orderPrefs.getString(Constants.SORT_ORDER_PLAYLIST, SortOrder.Asc.name)
            ?: return SortOrder.Asc

        return try {
            SortOrder.valueOf(order)
        } catch (e: Exception) {
            SortOrder.Asc
        }
    }

    fun setPlaylistSortOrder(order: SortOrder) {
        with(orderPrefs.edit()) {
            putString(Constants.SORT_ORDER_PLAYLIST, order.name)
            apply()
        }
    }
}