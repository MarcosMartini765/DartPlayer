    package com.martini.dartplayer.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.martini.dartplayer.presentation.components.settings.SettingsAppBar
import com.martini.dartplayer.presentation.components.settings.dataCollection.DataCollection
import com.martini.dartplayer.presentation.components.settings.local.LocalFilesSettings
import com.martini.dartplayer.presentation.components.settings.player.PlayerSettings

@ExperimentalMaterialApi
@Composable
fun SettingsScreen(navController: NavController) {

    val systemUiColor = rememberSystemUiController()

    val systemColor = MaterialTheme.colors.primary

    SideEffect {
        systemUiColor.setSystemBarsColor(
            systemColor
        )
    }

    Scaffold(
        topBar = { SettingsAppBar(navController) },
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                ) {
                    LocalFilesSettings()
                    PlayerSettings()
                    DataCollection()
                }
            }
        }
    )
}