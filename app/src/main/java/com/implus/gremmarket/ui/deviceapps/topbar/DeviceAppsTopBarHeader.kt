package com.implus.gremmarket.ui.deviceapps.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import com.implus.gremmarket.LocalNavigation
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.Destination
import com.implus.gremmarket.ui.components.ActionIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceAppsTopBarHeader(
    modifier: Modifier,
    colors: TopAppBarColors
) {
    val navController = LocalNavigation.current
    TopAppBar(
        modifier = modifier,
        colors = colors,
        title = {
            Text(
                text = "Управление устройством и приложениями",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            ActionIcon(
                onClick = { navController.navigate(Destination.HOME_GAMES.route) },
                icon = ImageVector.vectorResource(R.drawable.outline_arrow_back_24)
            )
        }
    )
}