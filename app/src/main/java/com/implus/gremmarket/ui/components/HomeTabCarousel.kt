package com.implus.gremmarket.ui.components

import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeTabCarousel( modifier: Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Рекомендуем", "Лучшее", "Платные", "Windows ПК", "Другие устройства", "Детям", "Категории")
    PrimaryScrollableTabRow(
        selectedTab,
        modifier = modifier,
        containerColor = Color.White,
        contentColor = PlayGreen,
        edgePadding = 0.dp,
        tabs = {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) PlayGreen else TextSecondary
                        )
                    }
                )
            }
        },
        divider = {}
    )
}