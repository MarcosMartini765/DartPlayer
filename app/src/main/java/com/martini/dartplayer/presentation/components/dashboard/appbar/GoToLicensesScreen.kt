package com.martini.dartplayer.presentation.components.dashboard.appbar

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.oss.licenses.OssLicensesActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.martini.dartplayer.R

@ExperimentalMaterialApi
@Composable
fun GoToLicensesScreen(
    onClick: () -> Unit
) {
    val context = LocalContext.current

    val description = stringResource(id = R.string.Licenses)

    fun navigate() {
        onClick()
        OssLicensesMenuActivity.setActivityTitle(description)
        context.startActivity(Intent(context, OssLicensesActivity::class.java))
    }

    ListItem(
        modifier = Modifier.clickable {
            navigate()
        },
        icon = {
            Icon(
                Icons.Filled.Receipt,
                contentDescription = description
            )
        },
        text = { Text(description) }
    )
}