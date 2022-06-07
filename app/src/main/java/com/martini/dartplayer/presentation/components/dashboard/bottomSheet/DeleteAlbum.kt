package com.martini.dartplayer.presentation.components.dashboard.bottomSheet

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.usecases.GetListOfUrisState
import com.martini.dartplayer.presentation.components.GetListOfUrisViewModel
import com.martini.dartplayer.presentation.components.dashboard.DeleteMusicViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@Composable
fun DashboardBottomSheetDeleteAlbum(
    album: AlbumWithSongs,
    onClick: () -> Unit,
    deleteMusic: DeleteMusicViewModel = hiltViewModel(),
    getListOfUrisViewModel: GetListOfUrisViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                deleteMusic(Selected(listOf(album)))
                onClick()
            }
        }

    fun startDeleteRequest(uris: List<Uri>) {
        val intent = MediaStore.createTrashRequest(context.contentResolver, uris, true)

        val request = IntentSenderRequest.Builder(intent.intentSender).build()

        launcher.launch(request)
    }

    fun delete() {
        getListOfUrisViewModel(Selected(listOf(album)))
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

    var shouldOpenDialog by remember {
        mutableStateOf(false)
    }

    fun hideDialog() {
        shouldOpenDialog = false
    }

    fun showDialog() {
        shouldOpenDialog = true
    }

    if (shouldOpenDialog) {
        AlertDialog(
            onDismissRequest = {
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

    ListItem(
        modifier = Modifier.clickable { showDialog() },
        icon = {
            Icon(
                Icons.Filled.Delete,
                contentDescription = stringResource(R.string.deleteAlbum)
            )
        },
        text = { Text(stringResource(R.string.delete)) }
    )
}