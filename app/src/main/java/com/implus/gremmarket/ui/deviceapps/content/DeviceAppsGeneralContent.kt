package com.implus.gremmarket.ui.deviceapps.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.ShieldMoon
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.components.MenuItemData
import com.implus.gremmarket.ui.components.RowMenuItem

@Composable
fun DeviceAppsGeneralContent(
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
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = 14.dp, end = 10.dp)
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
                                val textModifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .clickable(
                                        onClick = {},
                                        indication = null /*ripple(color = colorResource(R.color.ClickRippleGreen))*/,
                                        interactionSource = remember { MutableInteractionSource() },
                                        role = Role.Button
                                    )
                                    .padding(vertical = 12.dp)
                                Text(
                                    text = "Обновить все",
                                    modifier = textModifier,
                                    color = colorResource(R.color.MarketBlue),
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "Подробнее",
                                    modifier = textModifier,
                                    color = colorResource(R.color.MarketBlue),
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    )
                )

                val takenSpace = 71f
                val overallSpace = 115f
                RowMenuItem(
                    MenuItemData(
                        icon = Icons.Outlined.Dns,
                        "Занято ${takenSpace.toInt()} ГБ из ${overallSpace.toInt()} ГБ",
                        trailingContent = {
                            Spacer(modifier = Modifier.height(10.dp))
                            LinearProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                progress = { takenSpace / overallSpace },
                                color = colorResource(R.color.MarketBlue),
                                trackColor = colorResource(R.color.Shadow)
                            )
                        }
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