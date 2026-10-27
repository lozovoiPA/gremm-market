package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.ui.deviceapps.AppInfo

@Composable
fun ListCardsSection(
    apps: List<AppInfo> = emptyList()
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(start = 15.dp)
    ) {
/*
        items(apps.size) { index ->
            ListGameCard(
                apps[index]
            )
        } */

        items(apps.size / 3) { index ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ListGameCard(
                    apps[index*3]
                )
                ListGameCard(
                    apps[index*3+1]
                )
                ListGameCard(
                    apps[index*3+2]
                )
            }

        }
    }
}