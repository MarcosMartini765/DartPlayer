package com.martini.dartplayer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R

@Composable
fun Chip(
    text: String, icon: ImageVector?,
    onClick: () -> Unit
) {

    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Green,
        Color.Magenta,
        Color.Yellow,
        Color.Cyan,
    )

    Surface(
        modifier = Modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .height(40.dp)
            .clickable { onClick() },
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        color = colors.random()
    ) {
        Row(
            Modifier.width(120.dp).padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = stringResource(R.string.artist))
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text, maxLines = 1, overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2,
                color = Color.Black
            )
        }
    }
}