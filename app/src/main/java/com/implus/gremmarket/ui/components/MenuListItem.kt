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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary

data class MenuItemData (
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val trailingText: String? = null,
    val trailingContent: (@Composable () -> Unit)? = null
)

@Composable
fun MenuListItem(
    itemData: MenuItemData,
    modifier: Modifier = Modifier,
    colors: ListItemColors = ListItemDefaults.colors(containerColor = Color.Transparent),
    onClick: () -> Unit = {},
) {
    ListItem(
        modifier = modifier
            .clickable(
                onClick = { onClick() },
                indication = ripple(color = colorResource(R.color.ClickRippleGreen)), // Анимация зеленого эффекта по нажатию. Судя по докам должна быть по дефолту из М3, но почему-то у меня нет
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            ),
        leadingContent = {
            Icon(
                imageVector = itemData.icon,
                contentDescription = null,
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
        colors = colors
    )
}
