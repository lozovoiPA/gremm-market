package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.deviceapps.AppInfo

@Composable
fun BigGameCardCarousel() {
    val apps = listOf(
        AppInfo(
            "OFF",
            "191 МБ",
            "",
            genre = "Ролевые игры",
            iconImageId = R.drawable.off_icon,
            bannerImageId = R.drawable.off_banner
        ),
        AppInfo(
            "Another Eden",
            "1,7 ГБ",
            "",
            genre = "Ролевые игры",
            rating = "4,1",
            price = "255 ₽",
            iconImageId = R.drawable.ae_icon,
            bannerImageId = R.drawable.ae_banner
        ),
        AppInfo(
            "ENA: Dream BBQ",
            "1,2 ГБ",
            "",
            genre = "Приключения",
            iconImageId = R.drawable.dbbq_icon,
            bannerImageId = R.drawable.dbbq_banner
        ),
        AppInfo(
            "Bird Game",
            "",
            "",
            genre = "Ролевые игры",
            subgenre = "Симулятор",
            rating = "3,8",
            iconImageId = R.drawable.birdgame_icon,
            bannerImageId = R.drawable.birdgame_banner
        )
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(apps.size) { index ->
            BigGameCard(apps[index])
        }
    }
}