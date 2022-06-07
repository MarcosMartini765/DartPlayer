package com.martini.dartplayer.presentation.components.settings.player

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.player.PlaybackMode
import com.martini.dartplayer.domain.entity.player.PlayerSettings
import com.martini.dartplayer.domain.entity.player.copyWith
import com.martini.dartplayer.domain.usecases.playerSettings.GetPlayerSettingsState
import com.martini.dartplayer.presentation.components.settings.player.viewModels.GetPlayerSettingsViewModel
import com.martini.dartplayer.presentation.components.settings.player.viewModels.UpdatePlayerSettingsViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RespectAudioFocus(
    getPlayerSettingsViewModel: GetPlayerSettingsViewModel = hiltViewModel(),
    updatePlayerSettingsViewModel: UpdatePlayerSettingsViewModel = hiltViewModel()
) {

    val defaultSettings = PlayerSettings(
        respectAudioFocus = true,
        playbackMode = PlaybackMode.Loop
    )

    fun changeFocus(settings: PlayerSettings) {
        updatePlayerSettingsViewModel(settings.copyWith(respectAudioFocus = !settings.respectAudioFocus))
    }

    when (val state = getPlayerSettingsViewModel.state.value) {
        is GetPlayerSettingsState.Loaded -> {
            ListItem(
                text = {
                    Text(stringResource(R.string.playerSettingsRespectAudio))
                },
                secondaryText = {
                    Text(
                        if (state.settings.respectAudioFocus) {
                            stringResource(
                                R.string.playerSettingsWillRespectAudio,
                                stringResource(R.string.app_name)
                            )
                        } else {
                            stringResource(
                                R.string.playerSettingsWillNotRespectAudio,
                                stringResource(R.string.app_name)
                            )
                        }
                    )
                },
                trailing = {
                    Switch(
                        checked = state.settings.respectAudioFocus,
                        onCheckedChange = { changeFocus(state.settings) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary,
                            checkedTrackColor = MaterialTheme.colors.primary.copy(alpha = 0.7f)
                        )
                    )
                }
            )
        }
        is GetPlayerSettingsState.Loading -> {
            ListItem(
                text = { LinearProgressIndicator() },
                secondaryText = { LinearProgressIndicator() },
                trailing = {
                    CircularProgressIndicator()
                }
            )
        }
        else -> {
            ListItem(
                text = { Text(stringResource(R.string.playerSettingsRespectAudio)) },
                secondaryText = {
                    Text(
                        stringResource(
                            R.string.playerSettingsWillRespectAudio,
                            stringResource(R.string.app_name)
                        )
                    )
                },
                trailing = {
                    Switch(
                        checked = true,
                        onCheckedChange = { changeFocus(defaultSettings) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary,
                            checkedTrackColor = MaterialTheme.colors.primary.copy(alpha = 0.7f)
                        )
                    )
                }
            )
        }
    }
}