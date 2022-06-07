package com.martini.dartplayer.presentation.components.album.appbar.dropDown

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.album.LoadAlbumViewModel
import com.martini.dartplayer.presentation.components.album.models.DeleteAlbumForAlbumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AlbumAppBarDropDownDeleteAlbum(
    deleteAlbum: DeleteAlbumForAlbumViewModel = hiltViewModel(),
    loadAlbum: LoadAlbumViewModel = hiltViewModel(),
    onClick: () -> Unit,
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var dialogIsOpen by remember {
        mutableStateOf(false)
    }

    fun hideDialog() {
        dialogIsOpen = false
    }

    fun showDialog() {
        dialogIsOpen = true
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                when (val state = loadAlbum.state.value) {
                    is LoadAlbumState.Loaded -> {
                        deleteAlbum(state.albumWithSongAndArtists.songs)
                    }
                    else -> {}
                }
                hideDialog()
                onClick()
            }
        }

    fun startDeleteRequest(uris: List<Uri>) {
        val intent = MediaStore.createTrashRequest(context.contentResolver, uris, true)
        val request = IntentSenderRequest.Builder(intent.intentSender).build()

        launcher.launch(request)
    }

    fun delete() {
        when (val state = loadAlbum.state.value) {
            is LoadAlbumState.Loaded -> {
                getListOfUrisViewModel(Selected(state.albumWithSongAndArtists.songs.songs))
                    .flowOn(Dispatchers.IO)
                    .onEach {
                        when (it) {
                            is GetListOfUrisState.Loaded -> {
                                startDeleteRequest(it.uris)
                            }
                            else -> {}
                        }
                    }
                    .launchIn(scope)
            }
            else -> {}
        }
    }

    if (dialogIsOpen) {
        AlertDialog(
            onDismissRequest = {
                onClick()
                hideDialog()
            },
            title = { Text("${stringResource(R.string.deleteAlbum)}?") },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onClick()
                        hideDialog()
                    }) {
                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(onClick = {
                        playSoundOnTap(context)
                        delete()
                    }) {
                        Text(stringResource(R.string.yes))
                    }
                }
            }
        )
    }

    DropdownMenuItem(onClick = {
        playSoundOnTap(context)
        showDialog()
    }) {
        Text(stringResource(R.string.deleteAlbum))
    }
}