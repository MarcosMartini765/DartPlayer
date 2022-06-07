package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.common.extensions.removeReturn
import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.usecases.album.GetAlbumSortOrderState
import com.martini.dartplayer.domain.usecases.artist.GetArtistSortOrderState
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistSortOrderState
import com.martini.dartplayer.domain.usecases.song.GetSongSortOrderState
import com.martini.dartplayer.presentation.components.dashboard.appbar.sort.SortOrderGroup
import com.martini.dartplayer.presentation.components.dashboard.viewModels.*
import com.martini.dartplayer.presentation.components.viewModels.GetSongSortOrderViewModel

@ExperimentalMaterialApi
@Composable
fun SongSortOrder(
    getSongSortOrderViewModel: GetSongSortOrderViewModel = hiltViewModel(),
    setSongSortOrderViewModel: SetSongSortOrderViewModel = hiltViewModel(),
    getAlbumSortOrderViewModel: GetAlbumSortOrderViewModel = hiltViewModel(),
    setAlbumSortOrderViewModel: SetAlbumSortOrderViewModel = hiltViewModel(),
    getArtistSortOrderViewModel: GetArtistSortOrderViewModel = hiltViewModel(),
    setArtistSortOrderViewModel: SetArtistSortOrderViewModel = hiltViewModel(),
    getPlaylistSortOrderViewModel: GetPlaylistSortOrderViewModel = hiltViewModel(),
    setPlaylistSortOrderViewModel: SetPlaylistSortOrderViewModel = hiltViewModel(),
    pageStateViewModel: PageStateViewModel = hiltViewModel()
) {
    when (pageStateViewModel.state.value) {
        Constants.TABS_SONGS -> {
            when (val state = getSongSortOrderViewModel.state.value) {
                is GetSongSortOrderState.Loaded -> {
                    SortOrderGroup(
                        options = SortOrder.values().toList(),
                        title = stringResource(id = R.string.SortOrder),
                        initialSortOrder = state.order,
                        onSubmit = {
                            setSongSortOrderViewModel(it)
                        }
                    )
                }
                else -> Text(text = stringResource(id = R.string.somethingWentWrong))
            }
        }
        Constants.TABS_ALBUMS -> {
            when (val state = getAlbumSortOrderViewModel.state.value) {
                is GetAlbumSortOrderState.Loaded -> {
                    SortOrderGroup(
                        options = SortOrder.values().toMutableList()
                            .removeReturn(SortOrder.DateAdded),
                        title = stringResource(id = R.string.SortOrder),
                        initialSortOrder = state.order,
                        onSubmit = {
                            setAlbumSortOrderViewModel(it)
                        }
                    )
                }
                else -> Text(text = stringResource(id = R.string.somethingWentWrong))
            }
        }
        Constants.TABS_ARTISTS -> {
            when (val state = getArtistSortOrderViewModel.state.value) {
                is GetArtistSortOrderState.Loaded -> {
                    SortOrderGroup(
                        options = SortOrder.values().toMutableList()
                            .removeReturn(SortOrder.DateAdded),
                        title = stringResource(id = R.string.SortOrder),
                        initialSortOrder = state.order,
                        onSubmit = {
                            setArtistSortOrderViewModel(it)
                        }
                    )
                }
                else -> Text(text = stringResource(id = R.string.somethingWentWrong))
            }
        }
        Constants.TABS_PLAYLISTS -> {
            when (val state = getPlaylistSortOrderViewModel.state.value) {
                is GetPlaylistSortOrderState.Loaded -> {
                    SortOrderGroup(
                        options = SortOrder.values().toMutableList()
                            .removeReturn(SortOrder.DateAdded),
                        title = stringResource(id = R.string.SortOrder),
                        initialSortOrder = state.order,
                        onSubmit = {
                            setPlaylistSortOrderViewModel(it)
                        }
                    )
                }
                else -> Text(text = stringResource(id = R.string.somethingWentWrong))
            }
        }
    }
}