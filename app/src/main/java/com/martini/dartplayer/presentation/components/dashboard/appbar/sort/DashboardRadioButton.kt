package com.martini.dartplayer.presentation.components.dashboard.appbar.sort

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.martini.dartplayer.domain.entity.song.SortOrder

@ExperimentalMaterialApi
@Composable
fun DashboardRadioButton(
    text: String,
    onClick: (order: SortOrder) -> Unit,
    sortOrder: SortOrder,
    currentOrder: SortOrder
) {
    ListItem(
        modifier = Modifier.clickable { onClick(sortOrder) },
        icon = {
            RadioButton(
                selected = sortOrder == currentOrder,
                onClick = { onClick(sortOrder) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = if (MaterialTheme.colors.isLight) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.secondary
                    }
                )
            )
        },
        text = { Text(text) }
    )
}