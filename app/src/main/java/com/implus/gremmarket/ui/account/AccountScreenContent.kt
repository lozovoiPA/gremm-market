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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.LocalNavigation
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.components.MenuGroup
import com.implus.gremmarket.ui.components.MenuItemData
import com.implus.gremmarket.ui.components.MenuListItem

@Composable
fun AccountScreenContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
) {
    val navController = LocalNavigation.current

    val currentAccount = Account(
        "Vivego",
        "vividegoistical@gmail.com",
        Color(0xFF4285F4),
        profilePicture = painterResource(R.drawable.profile_pic_poppy)
    )
    val otherAccount = Account("П", "petrov@example.com", Color(0xFF9C27B0))

    LazyColumn(
        state = listState,
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
                AccountHeader(currentAccount, otherAccount)

                MenuGroup(
                    {
                        MenuListItem(
                            MenuItemData(
                                Icons.Outlined.Apps,
                                "Управление приложениями и устройством"
                            ),
                            onClick = { navController.navigate("device-apps") }
                        )
                    },
                    { MenuListItem(MenuItemData(Icons.Outlined.Notifications, "Уведомления и предложения", trailingText = "1")) },
                    { MenuListItem(MenuItemData(Icons.Outlined.Payment, "Платежи и подписки")) },
                    { MenuListItem(MenuItemData(Icons.Outlined.Security, "Play Защита")) },
                    { MenuListItem(MenuItemData(Icons.Outlined.Folder, "Библиотека")) },
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