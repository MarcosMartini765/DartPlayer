package com.martini.dartplayer.presentation.components.dashboard.appbar.sort

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.song.SortOrder

@ExperimentalMaterialApi
@Composable
fun SortOrderGroup(
    options: List<SortOrder>,
    title: String,
    initialSortOrder: SortOrder,
    onSubmit: (order: SortOrder) -> Unit
) {
    var sortOrder by remember {
        mutableStateOf(initialSortOrder)
    }

    val dialogIsOpen = remember {
        mutableStateOf(false)
    }

    fun hide() {
        dialogIsOpen.value = false
    }

    fun show() {
        dialogIsOpen.value = true
    }

    fun getText(order: SortOrder): Int {
        return when (order) {
            SortOrder.Asc -> R.string.SortAsc
            SortOrder.Desc -> R.string.SortDesc
            SortOrder.DateAdded -> R.string.SortDateAdded
        }
    }

    fun onChange(order: SortOrder) {
        sortOrder = order
    }

    fun update() {
        hide()
        onSubmit(sortOrder)
    }

    if (dialogIsOpen.value) {
        AlertDialog(
            onDismissRequest = { hide() },
            title = { Text(title) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    options.forEach { order ->
                        DashboardRadioButton(
                            text = stringResource(getText(order)),
                            onClick = { onChange(it) },
                            sortOrder = order,
                            currentOrder = sortOrder
                        )
                    }
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = { hide() }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(onClick = { update() }) {
                        Text(text = stringResource(id = R.string.select))
                    }
                }
            }
        )
    }

    ListItem(
        Modifier.clickable { show() },
        icon = {
            Icon(
                Icons.Default.Sort,
                contentDescription = stringResource(id = R.string.SortOrder)
            )
        },
        text = { Text(text = stringResource(id = R.string.SortOrder)) }
    )
}