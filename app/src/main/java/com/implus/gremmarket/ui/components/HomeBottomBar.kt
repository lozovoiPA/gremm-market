package com.implus.gremmarket.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        "Игры" to Icons.Filled.VideogameAsset,
        "Приложения" to Icons.Filled.GridView,
        "Поиск" to Icons.Filled.Search,
        "Книги" to Icons.Filled.Book,
        "Вы" to Icons.Outlined.Person
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.second, contentDescription = item.first) },
                label = { Text(item.first, fontSize = 12.sp) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PlayGreen,
                    selectedTextColor = PlayGreen,
                    indicatorColor = PlayGreen.copy(alpha = 0.1f),
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}