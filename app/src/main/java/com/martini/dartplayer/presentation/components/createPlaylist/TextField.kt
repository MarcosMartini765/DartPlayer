package com.martini.dartplayer.presentation.components.createPlaylist

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CreatePlaylistTextField(
    onChange: (newText: String) -> Unit,
    text: String
) {

    TextField(
        value = text,
        onValueChange = {
            onChange(it)
        },
        maxLines = 1,
        placeholder = {
            Text(
                "Create a playlist", style = TextStyle(
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
        shape = RoundedCornerShape(20)
    )
}