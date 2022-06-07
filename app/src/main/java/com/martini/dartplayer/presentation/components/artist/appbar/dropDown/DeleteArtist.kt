package com.martini.dartplayer.presentation.components.artist.appbar.dropDown

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
import com.martini.dartplayer.domain.usecases.artist.LoadArtistState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.artist.DeleteArtistForArtistViewModel
import com.martini.dartplayer.presentation.components.artist.LoadArtistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ArtistAppBarDeleteArtist(
    onClick: () -> Unit,
    deleteArtist: DeleteArtistForArtistViewModel = hiltViewModel(),
    loadArtist: LoadArtistViewModel = hiltViewModel(),
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                when (val state = loadArtist.state.value) {
                    is LoadArtistState.Loaded -> {
                        deleteArtist(state.data)
                        onClick()
                    }
                    else -> {}
                }
            }
        }

    var showAlert by remember {
        mutableStateOf(false)
    }

    fun showDialog() {
        showAlert = true
    }

    fun hideDialog() {
        showAlert = false
    }

    fun startDeleteRequest(uris: List<Uri>) {
        val intent = MediaStore.createTrashRequest(context.contentResolver, uris, true)

        val request = IntentSenderRequest.Builder(intent.intentSender).build()

        launcher.launch(request)
    }

    fun delete() {
        when (val state = loadArtist.state.value) {
            is LoadArtistState.Loaded -> {
                getListOfUrisViewModel(Selected(state.data.albums))
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

    if (showAlert) {
        AlertDialog(
            onDismissRequest = {
                hideDialog()
                onClick()
            },
            title = { Text("${stringResource(R.string.deleteArtist)}?") },
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
                        onClick()
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

    DropdownMenuItem(onClick = {
        showDialog()
    }) {
        Text(stringResource(R.string.deleteArtist))
    }
}