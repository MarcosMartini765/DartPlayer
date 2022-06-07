package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.NavigationViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel

@ExperimentalMaterialApi
@Composable
fun DashboardAppBarNavIconSelected(
    selected: SelectedMusicViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {

    var dialogShouldOpen by remember {
        mutableStateOf(false)
    }

    if (dialogShouldOpen) {
        AlertDialog(
            onDismissRequest = { dialogShouldOpen = false },
            title = { Text(stringResource(R.string.app_name)) },
            text = {
                Column {
                    ListItem(
                        modifier = Modifier.clickable {
                            dialogShouldOpen = false
                            navigationViewModel.navigateToSettings()
                        },
                        icon = {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = stringResource(R.string.settings)
                            )
                        },
                        text = { Text(stringResource(R.string.settings)) }
                    )
                    Divider()
                    /*GoToLicensesScreen(
                        onClick = { dialogShouldOpen = false }
                    )*/
                }
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { dialogShouldOpen = false }) {
                        Text(stringResource(R.string.back))
                    }
                }
            },
        )
    }

    if (selected.state.value.music.isNotEmpty()) {
        IconButton(onClick = { selected.clear() }) {
            Icon(Icons.Filled.Clear, contentDescription = stringResource(R.string.clear))
        }
    } else {
        IconButton(onClick = {
            dialogShouldOpen = true
        }) {
            Icon(Icons.Filled.Menu, stringResource(R.string.openNavigation))
        }
    }
}