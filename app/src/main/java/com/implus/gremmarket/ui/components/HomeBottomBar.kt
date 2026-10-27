package com.implus.gremmarket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.Book
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary

@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        "Игры" to Icons.Filled.SportsEsports,
        "Приложения" to Icons.Filled.GridView,
        "Поиск" to Icons.Filled.Search,
        "Книги" to Icons.Outlined.Book,
        "Вы" to Icons.Outlined.Person
    )

    Box(
        modifier = Modifier
            .background(color = colorResource(R.color.MarketAccent))
    ) {
        NavigationBar(
            modifier = modifier
                .height(103.dp)
                .padding(5.dp)
                .padding(bottom = 0.dp),
            containerColor = colorResource(R.color.MarketAccent),
            tonalElevation = 8.dp
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    modifier = Modifier.offset(y = 5.dp),
                    icon = { Icon(item.second, contentDescription = item.first) },
                    label = {
                        Text(
                            item.first,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(R.color.HomeBottomBarText),
                        selectedTextColor = colorResource(R.color.HomeBottomBarText),
                        indicatorColor = colorResource(R.color.HomeBottomBarIndicator),
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
            }
        }
    }
}