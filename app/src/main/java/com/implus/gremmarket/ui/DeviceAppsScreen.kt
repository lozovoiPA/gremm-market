package com.implus.gremmarket.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.ShieldMoon
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.components.PlayGreen
import com.implus.gremmarket.ui.components.TextSecondary

@Composable
fun DeviceAppsScreenScaffold(
    modifier: Modifier = Modifier,
    content: @Composable ((PaddingValues, Modifier) -> Unit) = { paddingValues, modifierInner -> DeviceAppsScreenContent(paddingValues, modifierInner) }
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = colorResource(R.color.MarketBackground),
        topBar = {
            DeviceAppsTopBar(
                modifier
                    .offset(y = (-3).dp)
            )
        }
    ) {
        contentPadding ->
        val innerPadding = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)

        val modifierInner = modifier
            .padding(
                start = innerPadding,
                end = innerPadding
            )

        content(contentPadding, modifierInner)
    }
}

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

    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
    ) {
        DeviceAppsTopBarHeader(
            modifierInner,
            topBarColors
        )
        Column(
            modifier = Modifier
                .offset(y = (-5).dp)
        ) {
            DeviceAppsTopBarTabs(
                modifierInner
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceAppsTopBarHeader(
    modifier: Modifier,
    colors: TopAppBarColors
) {
    TopAppBar(
        modifier = modifier,
        colors = colors,
        title = {
            Text(
                text = "Управление устройством и приложениями",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(3.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = {},
                        indication = ripple(color = colorResource(R.color.ClickRippleGray)),
                        interactionSource = remember { MutableInteractionSource() },
                        role = Role.Button
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}

@Composable
fun DeviceAppsTopBarTabs(
    modifier: Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Общие сведения", "Управление")
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

@Composable
fun DeviceAppsScreenContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
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
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
            ) {
                RowMenuItem(
                    MenuItemData(
                        icon = Icons.Outlined.ShieldMoon,
                        "Вредоносные приложения не найдены",
                        "Проверка выполнена вчера"
                    )
                )
                RowMenuItem(
                    MenuItemData(
                        icon = Icons.Outlined.GridView,
                        "Доступны обновления",
                        "6 обновлений ожидают скачивания.",
                        trailingContent = {
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = "Обновить все",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(30.dp))
                                        .clickable(
                                            onClick = {},
                                            indication = ripple(color = colorResource(R.color.ClickRippleGreen)),
                                            interactionSource = remember { MutableInteractionSource() },
                                            role = Role.Button
                                        )
                                        .padding(vertical = 12.dp),
                                    color = colorResource(R.color.ClickRippleGreen),
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "Подробнее",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(30.dp))
                                        .clickable(
                                            onClick = {},
                                            indication = ripple(color = colorResource(R.color.ClickRippleGreen)),
                                            interactionSource = remember { MutableInteractionSource() },
                                            role = Role.Button
                                        )
                                        .padding(vertical = 12.dp),
                                    color = colorResource(R.color.ClickRippleGreen),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    )
                )
                RowMenuItem(
                    MenuItemData(
                        icon = Icons.Outlined.Dns,
                        "71 ГБ из 115 ГБ"
                    )
                )
                RowMenuItem(
                    MenuItemData(
                        icon = Icons.Outlined.DevicesOther,
                        "Синхронизация приложений с устройствами",
                        "Синхронизированных устройств нет."
                    )
                )
                RowMenuItem(
                    MenuItemData(
                        icon = Icons.Outlined.Stars,
                        "Оценки и отзывы"
                    )
                )
                RowMenuItem(
                    MenuItemData(
                        icon = Icons.Outlined.PersonPin,
                        "Ваши впечатления"
                    )
                )
            }
        }
    }
}

@Composable
fun RowMenuItem(
    itemData: MenuItemData,
    onClick: () -> Unit = {}
) {
    val titleStyle = MaterialTheme.typography.bodyLarge
    val subtitleStyle = MaterialTheme.typography.bodyMedium

    Layout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            )
            .padding(vertical = 16.dp),
        content = {
            Icon(
                imageVector = itemData.icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Column {
                Text(
                    text = itemData.title,
                    style = titleStyle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (itemData.subtitle != null) {
                    Text(
                        text = itemData.subtitle,
                        style = subtitleStyle,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                itemData.trailingContent?.invoke()
            }

            if (itemData.trailingText != null) {
                Text(
                    text = itemData.trailingText,
                    style = subtitleStyle,
                    color = Color.Gray
                )
            }
        }
    ) { measurables, constraints ->
        // --- MEASUREMENT PHASE ---

        val totalWidth = constraints.maxWidth
        val iconMeasurable = measurables[0]
        val trailingMeasurable = measurables.getOrNull(2)

        // 1. Measure Icon and Trailing FIRST to know how much width they take
        val iconPlaceable = iconMeasurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
        val trailingPlaceable = trailingMeasurable?.measure(constraints.copy(minWidth = 0, minHeight = 0))

        val iconWidth = iconPlaceable.width
        val trailingWidth = trailingPlaceable?.width ?: 0
        val spacing = 32.dp.roundToPx()

        // 2. Calculate available width for the Text Column
        val columnMaxWidth = totalWidth - iconWidth - spacing - trailingWidth

        // 3. Measure the Column with the CORRECT max width constraint
        val columnMeasurable = measurables[1]
        val columnPlaceable = columnMeasurable.measure(
            constraints.copy(
                minWidth = 0,
                minHeight = 0,
                maxWidth = columnMaxWidth // <--- THIS PREVENTS THE OVERFLOW
            )
        )

        // 4. Calculate Title Height for icon alignment
        val titleLineHeightPx = with(titleStyle) {
            val lineHeightSp = if (lineHeight.value > 0f) lineHeight.value else fontSize.value
            (lineHeightSp * 2).sp
        }.toPx()

        // --- PLACEMENT PHASE ---
        val rowHeight = maxOf(
            columnPlaceable.height,
            iconPlaceable.height,
            trailingPlaceable?.height ?: 0
        )

        layout(totalWidth, rowHeight) {
            // 1. Place Icon (centered on first 2 lines of text)
            val iconCenterY = if (columnPlaceable.height < titleLineHeightPx) {
                columnPlaceable.height / 2
            } else {
                titleLineHeightPx / 2
            }
            val iconY = iconCenterY.toInt() - (iconPlaceable.height / 2)
            iconPlaceable.placeRelative(x = 0, y = iconY)

            // 2. Place Column
            columnPlaceable.placeRelative(x = iconWidth + spacing, y = 0)

            // 3. Place Trailing Text (right-aligned, vertically centered)
            if (trailingPlaceable != null) {
                val trailingY = (rowHeight - trailingPlaceable.height) / 2
                trailingPlaceable.placeRelative(
                    x = totalWidth - trailingPlaceable.width,
                    y = trailingY
                )
            }
        }
    }
}

@Composable
fun RowMenuItem2(
    itemData: MenuItemData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {},
                indication = ripple(color = colorResource(R.color.ClickRippleGray)),
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            )
            .padding(vertical = 16.dp)
    ) {
        Icon(
            imageVector = itemData.icon,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(Modifier.width(32.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(itemData.title, style = MaterialTheme.typography.bodyLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
            if (itemData.subtitle != null) {
                Text(itemData.subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            itemData.trailingContent?.invoke()
        }

        if (itemData.trailingText != null) {
            Text(itemData.trailingText, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
    }
}