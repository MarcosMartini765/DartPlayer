package com.martini.dartplayer.presentation.components.settings.local

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.ScanSongsState

@ExperimentalMaterialApi
@Composable
fun ScanMusicButton(
    model: ScanMusicViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            model.scanSongs()
        }
    }

    ListItem(

        text = { Text(stringResource(R.string.scanSongs)) },
        secondaryText = { Text(stringResource(R.string.scanSongsExplanation)) },
        trailing = {
            when (model.state.value) {
                is ScanSongsState.Loading -> {
                    CircularProgressIndicator()
                }
                else -> {
                    IconButton(onClick = {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) -> {
                                model.scanSongs()
                            }
                            else -> {
                                launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = stringResource(R.string.scanSongs),
                            modifier = Modifier.size(ButtonDefaults.IconSize * 2)
                        )
                    }
                }
            }
        }
    )
}