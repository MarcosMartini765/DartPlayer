package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.presentation.components.dashboard.viewModels.ExitAppViewModel

@ExperimentalMaterialApi
@Composable
fun DashboardExitApp(
    exitAppViewModel: ExitAppViewModel = hiltViewModel()
) {

    val description = stringResource(id = R.string.Exit)
    val context = LocalContext.current

    ListItem(
        modifier = Modifier.clickable {
            playSoundOnTap(context)
            exitAppViewModel()
        },
        icon = {
            Icon(Icons.Filled.ExitToApp, contentDescription = description)
        },
        text = { Text(description) }
    )
}