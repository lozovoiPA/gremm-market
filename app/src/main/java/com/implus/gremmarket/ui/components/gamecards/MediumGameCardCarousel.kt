package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.ui.deviceapps.AppInfo

@Composable
fun MediumCardCarousel() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val apps = listOf(
            AppInfo(
                name = "Jojo's Bizarre Adventure: GS",
                size = "",
                icon = "\uD835\uDC09⭑\uD835\uDC09\u200F⭑",
                rating = "3,8",
                iconBgColor = Color.Yellow
            ),
            AppInfo(
                name = "Genshin Impact",
                size = "",
                icon = "⚔️✨",
                rating = "4,5"
            ),
            AppInfo(
                name = "Minecraft",
                size = "",
                icon = "⛏️\ud83d\udfe9",
                rating = "4,7"
            ),
            AppInfo(
                name = "Candy Crush Saga",
                size = "",
                icon = "\uD83C\uDF6C\uD83D\uDC9A",
                rating = "4,6"
            ),
            AppInfo(
                name = "Terraria",
                size = "",
                icon = "\uD83C\uDF33\uD83D\uDDE1",
                rating = "4,4"
            ),
            AppInfo(
                name = "Monument Valley",
                size = "",
                icon = "\uD835\uDC02\uD83D\uDD3A",
                rating = "4,8"
            )
        )

        items(apps.size) { index ->
            MediumGameCard(apps[index])
        }
    }
}