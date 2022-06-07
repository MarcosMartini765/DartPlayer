package com.martini.dartplayer.presentation.components.albumForArtist.appbar

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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.domain.usecases.albumForArtist.DeleteAlbumForArtistState
import com.martini.dartplayer.domain.usecases.albumForArtist.LoadAlbumForArtistState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.albumForArtist.viewModels.DeleteAlbumForArtistViewModel
import com.martini.dartplayer.presentation.components.albumForArtist.viewModels.DeleteSongsForAlbumViewModel
import com.martini.dartplayer.presentation.components.albumForArtist.viewModels.LoadAlbumForArtistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun AlbumForArtistAppBarDelete(
    selectedMusic: SelectedMusicViewModel = hiltViewModel(),
    deleteSongsForAlbum: DeleteSongsForAlbumViewModel = hiltViewModel(),
    loadAlbumForArtistViewModel: LoadAlbumForArtistViewModel = hiltViewModel(),
    deleteAlbumForArtist: DeleteAlbumForArtistViewModel = hiltViewModel(),
    popBack: () -> Unit,
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel()
) {

    val album = loadAlbumForArtistViewModel.state.value
    val selected = selectedMusic.state.value.music

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                deleteSongsForAlbum(selected.filterIsInstance<Song>())
            }
        }

    if (album is LoadAlbumForArtistState.Loaded) {
        if (album.albumWithSongs.songs.isEmpty()) {
            LaunchedEffect(Unit) {
                deleteAlbumForArtist(album.albumWithSongs)
            }
        }
    }

    if (deleteAlbumForArtist.state.value is DeleteAlbumForArtistState.Loaded) {
        LaunchedEffect(Unit) {
            popBack()
        }
    }

    var showAlert by remember { mutableStateOf(false) }

    fun hideDialog() {
        showAlert = false
    }

    fun showDialog() {
        showAlert = true
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

    if (showAlert) {
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
                        playSoundOnTap(context)
                        hideDialog()
                        delete()
                    }) {
                        Text(stringResource(R.string.yes))
                    }
                }
            }
        )
    }

    if (selectedMusic.state.value.music.isNotEmpty()) {
        IconButton(onClick = {
            playSoundOnTap(context)
            showDialog()
        }) {
            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete))
        }
    }
}