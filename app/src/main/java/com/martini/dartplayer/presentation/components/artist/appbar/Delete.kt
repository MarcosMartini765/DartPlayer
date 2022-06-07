package com.martini.dartplayer.presentation.components.artist.appbar

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
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.domain.usecases.artist.DeleteArtistForArtistState
import com.martini.dartplayer.domain.usecases.artist.LoadArtistState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.artist.DeleteAlbumArtistViewModel
import com.martini.dartplayer.presentation.components.artist.DeleteArtistForArtistViewModel
import com.martini.dartplayer.presentation.components.artist.LoadArtistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ArtistAppBarDelete(
    selectedMusic: SelectedMusicViewModel = hiltViewModel(),
    deleteAlbum: DeleteAlbumArtistViewModel = hiltViewModel(),
    loadArtistViewModel: LoadArtistViewModel = hiltViewModel(),
    deleteArtistForArtist: DeleteArtistForArtistViewModel = hiltViewModel(),
    popBack: () -> Unit,
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val selected = selectedMusic.state.value.music
    val artist = loadArtistViewModel.state.value

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                when (artist) {
                    is LoadArtistState.Loaded -> {
                        deleteAlbum(
                            artist.data,
                            selected.filterIsInstance<AlbumWithSongs>()
                        )
                    }
                    else -> {}
                }
            }
        }

    fun startDeleteRequest(uris: List<Uri>) {
        val intent = MediaStore.createTrashRequest(context.contentResolver, uris, true)

        val request = IntentSenderRequest.Builder(intent.intentSender).build()

        launcher.launch(request)
    }

    fun delete() {
        when (artist) {
            is LoadArtistState.Loaded -> {
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
            else -> {}
        }
    }

    val shouldOpenDialog = remember {
        mutableStateOf(false)
    }

    fun hideDialog() {
        shouldOpenDialog.value = false
    }

    fun showDialog() {
        shouldOpenDialog.value = true
    }

    if (artist is LoadArtistState.Loaded) {
        if (artist.data.albums.isEmpty()) {
            LaunchedEffect(Unit) {
                deleteArtistForArtist(artist.data)
            }
        }
    }

    if (deleteArtistForArtist.state.value is DeleteArtistForArtistState.Loaded) {
        LaunchedEffect(Unit) {
            popBack()
        }
    }

    if (shouldOpenDialog.value) {
        AlertDialog(
            onDismissRequest = { hideDialog() },
            text = {
                Text(
                    "${stringResource(R.string.delete)} ${selectedMusic.state.value.music.count()} ${
                        stringResource(
                            R.string.album
                        )
                    }(s)?"
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        hideDialog()
                        playSoundOnTap(context)
                    }) {
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

    if (selected.isNotEmpty()) {
        IconButton(onClick = { showDialog() }) {
            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete))
        }
    }
}