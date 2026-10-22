package com.implus.gremmarket.ui.deviceapps.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.components.ActionIcon
import com.implus.gremmarket.ui.deviceapps.AppDeviceFilter
import com.implus.gremmarket.ui.deviceapps.LocalDeviceAppsState
import com.implus.gremmarket.ui.deviceapps.sampleApps

@Composable
fun DeviceAppsSelectionManagementTopBar() {
    val viewState = LocalDeviceAppsState.current
    val titleText = when (val selectedAppsCount = viewState.selectedApps.size) {
        1 -> "Выделено 1 приложение"
        2,3,4 -> "Выделено $selectedAppsCount приложения"
        else -> "Выделено $selectedAppsCount приложений"
    }

    val colors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = colorResource(R.color.MarketBackground)
    )

    Column(modifier = Modifier) {
        TopAppBar(
            colors = colors,
            title = {
                Text(
                    text = titleText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                ActionIcon(
                    onClick = { viewState.clearSelection() },
                    icon = Icons.Outlined.Close
                )
            },
            actions = {
                Row() {
                    if(viewState.appDeviceFilter == AppDeviceFilter.NOT_INSTALLED) {
                        ActionIcon(
                            onClick = {
                                viewState.startDownload(sampleApps)
                            },
                            icon = Icons.Outlined.Download
                        )
                    }
                    ActionIcon(
                        onClick = { },
                        icon = Icons.Outlined.Delete
                    )
                }
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}