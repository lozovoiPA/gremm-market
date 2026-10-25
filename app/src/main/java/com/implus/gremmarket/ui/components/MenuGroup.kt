package com.implus.gremmarket.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R

// Меню в виде карточки (несколько пунктов объединены задним фоном с закругленными углами)
@Composable
fun MenuGroup(
    menuItemsData: List<MenuItemData>,
    modifier: Modifier = Modifier,
    menuDivider: @Composable () -> Unit = { MenuDivider() }
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(30.dp),
        color = colorResource(R.color.AccountMenuCardBackground),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column() {
            menuItemsData.forEachIndexed { index, item ->
                MenuListItem(item)
                if (index < menuItemsData.lastIndex) {
                    menuDivider()
                }
            }
        }
    }
}

@Composable
fun MenuGroup(
    vararg menuListItems: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    menuDivider: @Composable () -> Unit = { MenuDivider() }
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = colorResource(R.color.AccountMenuCardBackground),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column() {
            menuListItems.forEachIndexed { index, item ->
                item()
                if (index < menuListItems.lastIndex) {
                    menuDivider()
                }
            }
        }
    }
}

// Разделитель внутри карточки между пунктами меню
@Composable
fun MenuDivider() {
    HorizontalDivider(
        thickness = 2.5.dp,
        color = colorResource(R.color.AccountBackground)
    )
}