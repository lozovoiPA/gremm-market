package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SmallCardsCarousel() {
    // Поскольку здесь нужна сетка 3 в ряд, но внутри LazyColumn,
    // мы используем Row с весами (weight) для распределения пространства.
    // В реальном приложении лучше использовать LazyVerticalGrid, но для вставки в LazyColumn это проще.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SmallGameCard(title = "Ultrapool", rating = "4,6")
        SmallGameCard(title = "Soul Knight", rating = "4,3")
        SmallGameCard(title = "Хорошая пицца", rating = "4,6")
    }
}