package com.implus.gremmarket.ui.deviceapps.content

import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.outlined.PlaylistRemove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindowProvider
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.MenuGroup
import com.implus.gremmarket.ui.MenuItemData
import com.implus.gremmarket.ui.MenuListItem
import com.implus.gremmarket.ui.deviceapps.AppDeviceFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDeviceFilterBottomSheet(
    sheetState: SheetState,
    appDeviceFilter: AppDeviceFilter,
    onClickDeviceFilter: () -> Unit = {},
    onClickNotInstalledFilter: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    menuModifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,

        // Затемнение экрана
        scrimColor = Color.Black.copy(alpha = 0.5f),
        containerColor = colorResource(R.color.MarketBackground),
        // Убираем дефолтную ручку перетаскивания
        dragHandle = {},
        shape = RectangleShape
    ) {
        // Делаем бар навигации прозрачным (этот модал считается отдельным окном)
        val view = LocalView.current
        val dialogWindow = (view.parent as? DialogWindowProvider)?.window

        SideEffect {
            dialogWindow?.let { window ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.isNavigationBarContrastEnforced = false
                }
                window.navigationBarColor = Color.Transparent.toArgb()
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .consumeWindowInsets(PaddingValues(horizontal = 16.dp))
                .navigationBarsPadding()
        ) {
            Text(
                "Выберите устройство",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                letterSpacing = 0.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                val selectedItemColors = ListItemDefaults.colors().copy(
                    containerColor = colorResource(R.color.MarketAccentBlue),
                    headlineColor = Color.Black,
                    leadingIconColor = Color.DarkGray
                )
                val itemColors = ListItemDefaults.colors().copy(
                    containerColor = Color.Transparent,
                    headlineColor = Color.DarkGray,
                    leadingIconColor = Color.DarkGray
                )

                MenuGroup(
                    {
                        MenuListItem(
                            MenuItemData(
                                Icons.Default.Smartphone,
                                "Это устройство"
                            ),
                            colors = if (appDeviceFilter == AppDeviceFilter.ON_DEVICE) selectedItemColors else itemColors,
                            onClick = onClickDeviceFilter
                        )
                    },
                    {
                        MenuListItem(
                            MenuItemData(
                                Icons.Outlined.PlaylistRemove,
                                "Не установленные"
                            ),
                            colors = if (appDeviceFilter == AppDeviceFilter.NOT_INSTALLED) selectedItemColors else itemColors,
                            onClick = onClickNotInstalledFilter
                        )
                    },
                    modifier = menuModifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)),
                    menuDivider = {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                    }
                )
            }

        }
    }
}