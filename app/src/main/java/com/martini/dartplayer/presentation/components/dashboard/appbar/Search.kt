package com.martini.dartplayer.presentation.components.dashboard.appbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.dashboard.LoadMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.viewModels.SearchTextViewModel

@Composable
fun DashboardSearch(
    loadMusicViewModel: LoadMusicViewModel = hiltViewModel(),
    searchTextViewModel: SearchTextViewModel = hiltViewModel()
) {

    val text = searchTextViewModel.state.value
    val focus = remember { FocusRequester() }

    DisposableEffect(Unit) {
        focus.requestFocus()
        onDispose { }
    }

    TextField(
        modifier = Modifier.focusRequester(focus),
        value = text,
        onValueChange = {
            searchTextViewModel(it)
            if (text.isNotEmpty()) {
                if (text.trim().length > 2) {
                    loadMusicViewModel.search(text)
                }
            }

        },
        maxLines = 1,
        placeholder = {
            Text(
                "${stringResource(R.string.search)}...", style = TextStyle(
                    color = Color.White,
                )
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.typography.body2.color,
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                Icons.Filled.Search, stringResource(R.string.search),
                tint = Color.White.copy(0.8f),
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { searchTextViewModel("") }) {
                    Icon(
                        Icons.Filled.Clear, stringResource(R.string.clear),
                        tint = Color.White.copy(0.8f),
                    )
                }
            }
        }
    )
}