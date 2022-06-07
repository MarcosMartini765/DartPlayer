package com.martini.dartplayer.presentation.components.playlist.bottomSheet

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.PlaylistDeleteSongsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@Composable
fun PlaylistBottomSheetDeleteSong(
    deleteMusic: PlaylistDeleteSongsViewModel = hiltViewModel(),
    song: Song,
    onClick: () -> Unit,
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val description = stringResource(id = R.string.delete)
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                deleteMusic(Selected(listOf(song)))
                onClick()
            }
        }

    fun startDeleteRequest(uris: List<Uri>) {
        val intent = MediaStore.createTrashRequest(context.contentResolver, uris, true)

        val request = IntentSenderRequest.Builder(intent.intentSender).build()

        launcher.launch(request)
    }

    fun delete() {
        playSoundOnTap(context)
        getListOfUrisViewModel(Selected(listOf(song)))
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is GetListOfUrisState.Loaded) {
                    startDeleteRequest(it.uris)
                }
            }
            .launchIn(scope)
    }

    ListItem(
        modifier = Modifier.clickable { delete() },
        icon = { Icon(Icons.Filled.Delete, contentDescription = description) },
        text = { Text(description) }
    )
}