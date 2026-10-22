package com.implus.gremmarket.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun ActionIcon(
    onClick: () -> Unit = {},
    icon: ImageVector,
    indication: Color? = null,
    modifier: Modifier = Modifier
        .size(48.dp)
        .padding(3.dp)
        .clip(CircleShape)
        .clickable(
            onClick = { onClick() },
            indication = if (indication != null) ripple(color = indication) else null,
            interactionSource = remember { MutableInteractionSource() },
            role = Role.Button
        ),
    iconModifier: Modifier = Modifier
        .size(24.dp)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = iconModifier,
            tint = Color.Unspecified
        )
    }
}