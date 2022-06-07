package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.components.dashboard.bottomAppBar.DashboardTabs

@ExperimentalMaterialApi
@Composable
fun DashboardAppBar(
    navController: NavController,
    tabIndex: Int = 0,
    onChange: (index: Int) -> Unit
) {
    Column {
        TopAppBar(
            title = {
                DashboardAppBarTitle()
            },
            navigationIcon = {
                DashboardAppBarNavIcon()
            },
            actions = {
                SearchButton()
                DashboardAppBarDelete(navController)
                DashboardMoreButton()
            }
        )
        DashboardTabs(tabIndex = tabIndex, onChange = onChange)
    }
}