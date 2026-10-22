package com.implus.gremmarket.ui.deviceapps.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R

@Composable
fun DeviceAppsTopBar(
    modifier: Modifier
) {
    // Задаем параметры для элементов топ бара (состоит из "заголовка", вкладок и разделителя)
    // Разделитель отдельно поскольку у него нет дополнительного паддинга (см. modifierInner)
    val modifierInner = modifier
        .padding(
            horizontal = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)
        )
    val topBarColors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = colorResource(R.color.MarketBackground)
    )


    Column(modifier = Modifier) {
        DeviceAppsTopBarHeader(
            modifierInner,
            topBarColors
        )
        Column(modifier = Modifier
            .offset(y = (-10).dp)
        ) {
            DeviceAppsTopBarTabs(
                modifier = modifierInner
            )
            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }
    }
}