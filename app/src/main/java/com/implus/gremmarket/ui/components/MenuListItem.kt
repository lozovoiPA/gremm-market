package com.implus.gremmarket.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary

data class MenuItemData (
    val icon: ImageVector? = null,
    val title: String,
    val subtitle: String? = null,
    val trailingText: String? = null,
    val trailingContent: (@Composable () -> Unit)? = null,
    val iconComposable: (@Composable () -> Unit)? = null,
    val subtitleContent: (@Composable () -> Unit)? = null
)

@Composable
fun MenuListItem(
    itemData: MenuItemData,
    modifier: Modifier = Modifier,
    colors: ListItemColors = ListItemDefaults.colors(containerColor = Color.Transparent),
    onClick: () -> Unit = {},
) {
    val textOffset = 8
    var titleOffset = 0

    if(itemData.subtitle != null) {
        titleOffset = -2
    }

    val offsetModifier = Modifier // Переносим текст ближе к иконке как в GP. ListItem автоматически делает одинаковые промежутки между границой-иконкой и иконкой-текстом
        .padding(end = textOffset.dp)
        .offset(x = (-textOffset).dp)

    ListItem(
        modifier = modifier
            .clickable(
                onClick = { onClick() },
                indication = ripple(color = colorResource(R.color.ClickRippleGreen)), // Анимация зеленого эффекта по нажатию. Судя по докам должна быть по дефолту из М3, но почему-то у меня нет
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            ),
        leadingContent = {
            if(itemData.icon != null) {
                Icon(
                    imageVector = itemData.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(26.dp)
                )
            } else if (itemData.iconComposable != null) {
                itemData.iconComposable()
            }
        },
        headlineContent = {
            Text(
                text = itemData.title,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = offsetModifier
            )
        },
        supportingContent = itemData.subtitle?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    modifier = offsetModifier
                        .padding(top = 4.dp)
                )
            }
        },
        trailingContent = when {
            itemData.trailingText != null -> {
                {
                    Text(
                        itemData.trailingText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                    )
                }
            }
            itemData.trailingContent != null -> itemData.trailingContent
            else -> null
        },
        colors = colors
    )
}
