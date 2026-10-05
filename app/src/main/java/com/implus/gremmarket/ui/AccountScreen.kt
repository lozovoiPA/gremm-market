package com.implus.gremmarket.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.components.TextPrimary
import com.implus.gremmarket.ui.components.TextSecondary


@Composable
fun AccountScreenScaffold(
    modifier: Modifier = Modifier,
    content: @Composable ((PaddingValues, Modifier) -> Unit) = { paddingValues, modifier -> }
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = colorResource(R.color.AccountBackground)
    ) {
        contentPadding ->
        var innerPadding = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)
        innerPadding *= 2

        val modifierInner = modifier
            .padding(
                start = innerPadding,
                end = innerPadding
            )

        content(contentPadding, modifierInner)
    }
}

@Composable
fun AccountScreenContent(
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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MenuGroup(
                    menuItems = listOf(
                        MenuItemData(Icons.Outlined.GridView, "Управление приложениями и устройством"),
                        MenuItemData(Icons.Outlined.Notifications, "Уведомления и предложения", trailingText = "1"),
                        MenuItemData(Icons.Outlined.Payment, "Платежи и подписки"),
                        MenuItemData(Icons.Outlined.Security, "Play Защита"),
                        MenuItemData(Icons.Outlined.Science, "Play Labs"),
                        MenuItemData(Icons.Outlined.Folder, "Библиотека")
                    )
                )
                MenuGroup (
                    menuItems = listOf(
                        MenuItemData(icon = Icons.Outlined.ManageAccounts, title = "Персонализация в приложении \"Google Play\""),
                        MenuItemData(icon = Icons.Outlined.Settings, title = "Настройки"),
                        MenuItemData(icon = Icons.Outlined.Help, title = "Справка/отзыв")
                    )
                )
                Footer()
            }
        }
    }
}

// Меню в виде карточки (несколько пунктов объединены задним фоном с закругленными углами)
@Composable
fun MenuGroup(
    menuItems: List<MenuItemData>
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(30.dp),
        color = colorResource(R.color.AccountMenuCardBackground),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column() {
            menuItems.forEachIndexed { index, item ->
                MenuListItem(item)
                if (index < menuItems.lastIndex) {
                    MenuDivider()
                }
            }
        }
    }
}

data class MenuItemData (
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val trailingText: String? = null,
    val trailingContent: (@Composable () -> Unit)? = null
)

@Composable
fun MenuListItem(
    itemData: MenuItemData
) {
    ListItem(
        modifier = Modifier
            .clickable(
                onClick = {},
                indication = ripple(color = colorResource(R.color.ClickRippleColor)), // Анимация зеленого эффекта по нажатию. Судя по докам должна быть по дефолту из М3, но почему-то у меня нет
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            ),
        leadingContent = {
            Icon(
                imageVector = itemData.icon,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier
                    .size(26.dp)
            )
        },
        headlineContent = {
            val textOffset = 8
            Text(
                text = itemData.title,
                style = MaterialTheme.typography.titleSmall,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 0.em,
                color = TextPrimary,
                fontWeight = FontWeight(500),
                fontSize = 3.7.em,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier // Переносим текст ближе к иконке как в GP. ListItem автоматически делает одинаковые промежутки между границой-иконкой и иконкой-текстом
                    .padding(end = textOffset.dp)
                    .offset(x = (-textOffset).dp)
            )
        },
        supportingContent = itemData.subtitle?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        },
        trailingContent = when {
            itemData.trailingText != null -> {
                {
                    Text(
                        itemData.trailingText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
            itemData.trailingContent != null -> itemData.trailingContent
            else -> null
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

// Разделитель внутри карточки между пунктами меню
@Composable
fun MenuDivider() {
    HorizontalDivider(
        thickness = 2.5.dp,
        color = colorResource(R.color.AccountBackground)
    )
}

@Composable
fun Footer() {
    // BoxWithConstraints позволяет определить, находимся ли мы в ландшафтном режиме
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Фиксируем текущую ориентацию экрана при ее изменении.
        var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
        val configuration = LocalConfiguration.current

        LaunchedEffect(configuration) {
            snapshotFlow { configuration.orientation }
                .collect { orientation = it }
        }

        // Расположение текста меняется в зависимости от ориентации экрана.
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Политика конфиденциальности", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Spacer(Modifier.padding(start = 14.dp))
                    Box(
                        modifier = Modifier
                            .size(3.dp)
                            .clip(CircleShape)
                            .background(TextSecondary)
                    )
                    Spacer(Modifier.padding(start = 14.dp))
                    Text("Условия использования", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Политика конфиденциальности",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.padding(10.dp))
                    Box(
                        modifier = Modifier
                            .size(3.dp)
                            .clip(CircleShape)
                            .background(TextSecondary)
                    )
                    Spacer(Modifier.padding(9.dp))
                    Text(
                        text = "Условия использования",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}