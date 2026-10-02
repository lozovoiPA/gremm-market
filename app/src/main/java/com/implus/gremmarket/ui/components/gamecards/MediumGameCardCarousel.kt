package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MediumCardCarousel() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(6) { index ->
            MediumGameCard(
                title = "Soul Knight Prequel",
                rating = "4,3"
            )
        }
    }
}