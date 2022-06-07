package com.martini.dartplayer.presentation.components.album.appbar

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.album.LoadAlbumViewModel
import com.martini.dartplayer.presentation.components.album.models.DeleteSongForAlbumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AlbumAppBarDelete(
    selectedMusic: SelectedMusicViewModel = hiltViewModel(),
    deleteSongs: DeleteSongForAlbumViewModel = hiltViewModel(),
    loadAlbum: LoadAlbumViewModel = hiltViewModel(),
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel()
) {
    val selected = selectedMusic.state.value.music
    val albumState = loadAlbum.state.value

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                val selectedSongs = selected.filterIsInstance<Song>()
                deleteSongs(selectedSongs)
            }
        }

    fun startDeleteRequest(uris: List<Uri>) {

        val intent = MediaStore.createTrashRequest(context.contentResolver, uris, true)
        val request = IntentSenderRequest.Builder(intent.intentSender).build()

        launcher.launch(request)
    }

    fun delete() {
        getListOfUrisViewModel(selectedMusic.state.value)
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


    val dialogShouldOpen = remember {
        mutableStateOf(false)
    }

    fun hideDialog() {
        dialogShouldOpen.value = false
    }

    fun showDialog() {
        dialogShouldOpen.value = true
    }

    if (dialogShouldOpen.value) {
        AlertDialog(
            onDismissRequest = { hideDialog() },
            title = {
                Text(
                    "${stringResource(R.string.delete)} ${selected.count()} ${stringResource(R.string.song)}(s)?"
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { hideDialog() }) {
                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(onClick = {
                        if (albumState is LoadAlbumState.Loaded) {
                            delete()
                        }
                        hideDialog()
                    }) {
                        Text(stringResource(R.string.yes))
                    }
                }
            }
        )
    }

    if (selected.isNotEmpty()) {
        IconButton(onClick = {
            showDialog()
        }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete")
        }
    }
}