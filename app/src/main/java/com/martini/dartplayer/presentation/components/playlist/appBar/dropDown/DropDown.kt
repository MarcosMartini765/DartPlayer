package com.martini.dartplayer.presentation.components.playlist.appBar.dropDown

import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.playlist.DeletePlaylistState
import com.martini.dartplayer.presentation.components.playlist.viewModels.DeletePlaylistViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun PlaylistAppBarDropDown(
    popBack: () -> Unit,
    deletePlaylistViewModel: DeletePlaylistViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        snapshotFlow { deletePlaylistViewModel.state.value }
            .distinctUntilChanged()
            .drop(1)
            .onEach {
                if (it is DeletePlaylistState.Loaded) {
                    popBack()
                }
            }
            .launchIn(this)
    }

    var isOpen by remember {
        mutableStateOf(false)
    }

    fun close() {
        isOpen = false
    }

    fun open() {
        isOpen = true
    }

    DropdownMenu(expanded = isOpen, onDismissRequest = { close() }) {
        PlaylistAppBarDeletePlaylist(onClick = { close() })
        PlaylistDropDownRemoveFromPlaylist(onClick = { close() })
    }

    val description = stringResource(id = R.string.more)

    IconButton(onClick = { open() }) {
        Icon(Icons.Filled.MoreVert, contentDescription = description)
    }
}