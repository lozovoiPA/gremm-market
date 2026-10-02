package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BigGameCardCarousel() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(5) { index ->
            BigGameCard(
                title = "Деревня Драконов 3",
                rating = "4,4",
                size = "0,90 ГБ",
                imageRes = 0 // Заглушка
            )
        }
    }
}