package com.implus.gremmarket.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.LocalNavigation
import com.implus.gremmarket.ui.components.MenuGroup
import com.implus.gremmarket.ui.components.MenuItemData
import com.implus.gremmarket.ui.components.MenuListItem

@Composable
fun AccountScreenContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val navController = LocalNavigation.current
    LazyColumn(
        modifier = Modifier // Модификатор для паддинга с учетом TopBar, BottomBar, и системных треев.
            .fillMaxSize()
            .padding(contentPadding) // Внешний паддинг вычисляемый скаффолдом - учитывает его TopBar и BottomBar
            .consumeWindowInsets(contentPadding) // Паддинг выше устанавливается для всех вложенных элементов
            .windowInsetsPadding( // Учитываем системные треи
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal
                )
            )
    )
    {
        item {
            Column( // Вложенная колонка позволяет скроллить по паддингу (СМ. альбомную ориентацию), в то время как просто LazyColumn не дает этого эффекта.
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val menuItemsList = listOf(
                    MenuItemData(
                        Icons.Outlined.GridView,
                        "Управление приложениями и устройством"
                    ),
                    MenuItemData(Icons.Outlined.Notifications, "Уведомления и предложения", trailingText = "1"),
                    MenuItemData(Icons.Outlined.Payment, "Платежи и подписки"),
                    MenuItemData(Icons.Outlined.Security, "Play Защита"),
                    MenuItemData(Icons.Outlined.Science, "Play Labs"),
                    MenuItemData(Icons.Outlined.Folder, "Библиотека")
                )
                MenuGroup(
                    {
                        MenuListItem(
                            MenuItemData(
                                Icons.Default.Smartphone,
                                "Это устройство"
                            ),
                            onClick = { navController.navigate("device-apps") }
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(30.dp))
                )
                MenuGroup (
                    menuItemsData = listOf(
                        MenuItemData(icon = Icons.Outlined.ManageAccounts, title = "Персонализация в приложении \"Google Play\""),
                        MenuItemData(icon = Icons.Outlined.Settings, title = "Настройки"),
                        MenuItemData(icon = Icons.Outlined.Help, title = "Справка/отзыв")
                    )
                )
                AccountFooter()
            }
        }
    }
}