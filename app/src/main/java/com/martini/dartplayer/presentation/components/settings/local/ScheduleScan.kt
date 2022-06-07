package com.martini.dartplayer.presentation.components.settings.local

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.files.GetLocalFilesState

@ExperimentalMaterialApi
@Composable
fun ScheduleScan(
    model: ScanFrequencyViewModel = hiltViewModel()
) {

    val state = model.state.value

    var dialogShouldOpen by remember { mutableStateOf(false) }
    var freq by remember {
        when (state) {
            is GetLocalFilesState.Loaded -> {
                mutableStateOf(state.settings.frequency)
            }
            else -> {
                mutableStateOf(Frequency.NEVER)
            }
        }
    }

    if (dialogShouldOpen) {

        if (state is GetLocalFilesState.Loaded) {
            freq = state.settings.frequency
        }

        AlertDialog(onDismissRequest = {
            dialogShouldOpen = false
        },
            title = { Text(stringResource(R.string.scanFrequency)) },
            text = {
                Column {
                    RadioItem(
                        text = stringResource(R.string.never),
                        frequency = Frequency.NEVER,
                        onClick = {
                            freq = it
                        },
                        value = freq
                    )
                    RadioItem(
                        text = stringResource(R.string.onceADay),
                        frequency = Frequency.ONCE_DAY,
                        onClick = {
                            freq = it
                        },
                        value = freq
                    )
                    RadioItem(
                        text = stringResource(R.string.twiceAWeek),
                        frequency = Frequency.ONCE_WEEK,
                        onClick = {
                            freq = it
                        },
                        value = freq
                    )
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.End,

                    ) {
                    TextButton(
                        onClick = { dialogShouldOpen = false }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.size(16.dp, 0.dp))
                    Button(
                        onClick = {
                            dialogShouldOpen = false
                            if (state is GetLocalFilesState.Loaded) {
                                model.updateSettings(
                                    state.settings.copy(
                                        frequency = freq
                                    )
                                )
                            }
                        }
                    ) {
                        Text(stringResource(R.string.select))
                    }
                }
            }
        )
    }

    ListItem(
        text = { Text(stringResource(R.string.automaticScan)) },
        secondaryText = { Text(stringResource(R.string.automaticScanExplanation)) },
        trailing = {
            IconButton(onClick = { dialogShouldOpen = true }) {
                Icon(
                    Icons.Filled.Autorenew,
                    contentDescription = stringResource(R.string.automaticScanExplanation),
                    modifier = Modifier.size(ButtonDefaults.IconSize * 2)
                )
            }
        }
    )
}