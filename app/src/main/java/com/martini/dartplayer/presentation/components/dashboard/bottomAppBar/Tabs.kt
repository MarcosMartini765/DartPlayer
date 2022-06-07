package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.martini.dartplayer.R

@Composable
fun DashboardTabs(
    tabIndex: Int,
    onChange: (Int) -> Unit
) {

    val tabList = listOf(
        stringResource(R.string.Songs),
        stringResource(R.string.Albums),
        stringResource(R.string.Artists),
        stringResource(R.string.Playlists)
    )

    val icons = listOf(
        Icons.Filled.Audiotrack,
        Icons.Filled.Album,
        Icons.Filled.ArtTrack,
        Icons.Filled.PlaylistPlay
    )

    TabRow(
        backgroundColor = if (MaterialTheme.colors.isLight) {
            MaterialTheme.colors.primarySurface
        } else {
            Color.Transparent
        },
        selectedTabIndex = tabIndex,
        tabs = {
            tabList.forEachIndexed { index, tab ->
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        onChange(index)
                    },
                    icon = { Icon(icons[index], contentDescription = tab) },
                    text = {
                        Text(
                            text = tab,
                            style = TextStyle(
                                fontSize = 13.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                )
            }
        })
}