package com.martini.dartplayer.presentation.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap

@Composable
fun MoreButton(
    content: @Composable () -> Unit
) {

    val context = LocalContext.current

    var menuState by remember {
        mutableStateOf(false)
    }

    fun hideMenu() {
        menuState = false
    }

    fun showMenu() {
        playSoundOnTap(context)
        menuState = true
    }

    val description = stringResource(id = R.string.openNavigation)

    DropdownMenu(expanded = menuState, onDismissRequest = { hideMenu() }) {
        content()
    }

    IconButton(onClick = { showMenu() }) {
        Icon(Icons.Filled.MoreVert, contentDescription = description)
    }
}