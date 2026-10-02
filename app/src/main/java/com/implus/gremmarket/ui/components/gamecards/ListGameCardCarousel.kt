package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListCardsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        ListGameCard(
            title = "Backpack Brawl — Битва героев",
            description = "Эпические битвы, герои и магия в средневековье. Организация, тактика и ст...",
            size = "184 МБ"
        )
        ListGameCard(
            title = "Soul Knight Prequel",
            description = "Пиксельная ролевая экшн-игра с невероятными приключениями",
            size = "468 МБ"
        )
    }
}