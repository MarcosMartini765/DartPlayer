package com.martini.dartplayer.presentation.components.playlist.appBar

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.PlaylistDeleteSongsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun PlaylistAppBarDeleteSelected(
    selectedMusicViewModel: SelectedMusicViewModel = hiltViewModel(),
    playlistDeleteSongsViewModel: PlaylistDeleteSongsViewModel = hiltViewModel(),
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel()
) {
    if (selectedMusicViewModel.state.value.music.isEmpty()) return

    val context = LocalContext.current
    val description = stringResource(id = R.string.delete)
    val scope = rememberCoroutineScope()

    fun delete() {
        playlistDeleteSongsViewModel(selectedMusicViewModel.state.value)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                delete()
            }
        }

    fun startDeleteRequest(uris: List<Uri>) {
        val intent = MediaStore.createTrashRequest(context.contentResolver, uris, true)

        val request = IntentSenderRequest.Builder(intent.intentSender).build()

        launcher.launch(request)
    }

    fun onClick() {
        getListOfUrisViewModel(selectedMusicViewModel.state.value)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is GetListOfUrisState.Loaded) {
                    startDeleteRequest(it.uris)
                }
            }
            .launchIn(scope)
    }

    IconButton(onClick = { onClick() }) {
        Icon(Icons.Filled.Delete, contentDescription = description)
    }
}