package com.martini.dartplayer.presentation.components.settings.local

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@ExperimentalMaterialApi
@Composable
fun RadioItem(
    text: String,
    frequency: Frequency,
    onClick: (Frequency) -> Unit,
    value: Frequency
) {
    ListItem(
        icon = {
            RadioButton(
                selected = value == frequency,
                onClick = { onClick(frequency) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = if (MaterialTheme.colors.isLight) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.secondary
                    }
                )
            )
        },
        text = { Text(text) },
        modifier = Modifier.clickable {
            onClick(frequency)
        }
    )
}