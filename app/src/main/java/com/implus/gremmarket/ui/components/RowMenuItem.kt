package com.implus.gremmarket.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary
import kotlin.math.abs

@Composable
fun RowMenuItem(
    itemData: MenuItemData,
    onClick: () -> Unit = {},
    clickIndication: Indication? = null,
    textOffset: Int = 0,
    titleStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    subtitleStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val offsetModifier = Modifier
        .padding(end = abs(textOffset).dp)
        .offset(x = (textOffset).dp)

    Layout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = clickIndication,
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            )
            .padding(vertical = 16.dp),
        content = {
            itemData.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
            itemData.iconComposable?.let { it() }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                /*
                Text(
                    text = itemData.title,
                    style = titleStyle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                ) */

                Text(
                    text = itemData.title,
                    style = titleStyle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = offsetModifier
                )

                if(itemData.subtitle != null) {
                    Text(
                        text = itemData.subtitle,
                        style = subtitleStyle,
                        color = TextSecondary,
                        modifier = offsetModifier
                            .padding(top = 4.dp)
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
        itemData.icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier
                    .size(24.dp)
            )
        }
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