package com.martini.dartplayer.presentation.components.settings.dataCollection

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.settings.GetCrashlyticsEnabledState
import com.martini.dartplayer.presentation.components.settings.dataCollection.viewModels.GetCrashlyticsEnableViewModel
import com.martini.dartplayer.presentation.components.settings.dataCollection.viewModels.SetTechnicalDataCollectionEnabledViewModel

@ExperimentalMaterialApi
@Composable
fun TechnicalData(
    setTechnicalDataCollectionEnabledViewModel: SetTechnicalDataCollectionEnabledViewModel = hiltViewModel(),
    getCrashlyticsEnableViewModel: GetCrashlyticsEnableViewModel = hiltViewModel()
) {

    fun onCheckedChange(value: Boolean) {
        setTechnicalDataCollectionEnabledViewModel(value)
    }

    ListItem(
        text = { Text(text = stringResource(id = R.string.TechnicalData)) },
        secondaryText = { Text(text = stringResource(id = R.string.TechnicalDataEx)) },
        trailing = {
            when (val state = getCrashlyticsEnableViewModel.state.value) {
                is GetCrashlyticsEnabledState.Loaded -> {
                    Switch(
                        checked = state.enabled,
                        onCheckedChange = { onCheckedChange(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary,
                            checkedTrackColor = MaterialTheme.colors.primary.copy(alpha = 0.7f)
                        )
                    )
                }
                is GetCrashlyticsEnabledState.Failure -> {
                    Icon(
                        Icons.Filled.Error,
                        contentDescription = stringResource(id = R.string.somethingWentWrong)
                    )
                }
                else -> {
                    CircularProgressIndicator()
                }
            }
        }
    )
}