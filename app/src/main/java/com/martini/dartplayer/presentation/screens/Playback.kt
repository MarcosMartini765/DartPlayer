package com.martini.dartplayer.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.components.dashboard.viewModels.SessionViewModel
import com.martini.dartplayer.presentation.components.playback.appbar.PlaybackAppBar
import com.martini.dartplayer.presentation.components.playback.bottomSheet.PlaybackBottomSheet
import com.martini.dartplayer.presentation.components.playback.content.PlaybackContent
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PlaybackScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = hiltViewModel()
) {

    if (!sessionViewModel.state.value) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )

    fun toggleBottomSheet() {
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.apply {
                if (isCollapsed) expand()
                else collapse()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            PlaybackBottomSheet(
                onClick = { toggleBottomSheet() }
            )
        },
        sheetPeekHeight = 60.dp,
        sheetShape = RoundedCornerShape(
            topEndPercent = 20,
            topStartPercent = 20,
        )
    ) {
        Scaffold(
            modifier = Modifier.padding(it),
            scaffoldState = scaffoldState,
            topBar = {
                PlaybackAppBar(
                    popBack = { navController.popBackStack() }
                )
            },
            content = { PlaybackContent() },
        )
    }
}