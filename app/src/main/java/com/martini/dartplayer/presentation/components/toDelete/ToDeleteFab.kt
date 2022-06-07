package com.martini.dartplayer.presentation.components.toDelete

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.usecases.DeleteMusicState
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.DeleteMusicViewModel
import com.martini.dartplayer.presentation.components.toDelete.viewModels.DeletePlaylistsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ToDeleteFab(
    selected: SelectedMusicViewModel = hiltViewModel(),
    deleteMusic: DeleteMusicViewModel = hiltViewModel(),
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel(),
    deletePlaylistsViewModel: DeletePlaylistsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                deleteMusic(selected.state.value)
            }
        }

    fun startDeleteRequest(uris: List<Uri>) {

        if (uris.isEmpty()) return

        playSoundOnTap(context)

        val intent = MediaStore.createTrashRequest(context.contentResolver, uris, true)

        val request = IntentSenderRequest.Builder(intent.intentSender).build()

        launcher.launch(request)
    }

    fun delete() {
        deletePlaylistsViewModel(selected.state.value.music.filterIsInstance<PlaylistWithSongs>())
        val toRemove = selected.state.value.music.filterNot { it is PlaylistWithSongs }
        getListOfUrisViewModel(Selected(toRemove))
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

    FloatingActionButton(onClick = {
        delete()
    }) {
        when (deleteMusic.state.value) {
            is DeleteMusicState.Loading -> {
                CircularProgressIndicator()
            }
            else -> {
                Text(stringResource(R.string.delete))
            }
        }
    }
}