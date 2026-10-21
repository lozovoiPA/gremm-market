package com.implus.gremmarket.ui.deviceapps.applist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.deviceapps.AppDeviceFilter

@Composable
fun AppListTitle(
    appDeviceFilter: AppDeviceFilter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            if (appDeviceFilter == AppDeviceFilter.ON_DEVICE) "Игры и приложения" else "Приложения",
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth(0.4f),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier
                .requiredWidth(200.dp)
                .fillMaxHeight()
                .clickable(
                    onClick = { },
                    indication = ripple(color = colorResource(R.color.ClickRippleGray)), // Анимация зеленого эффекта по нажатию. Судя по докам должна быть по дефолту из М3, но почему-то у меня нет
                    interactionSource = remember { MutableInteractionSource() },
                    role = Role.Button
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Недавно обновленные",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.width(7.dp))
            Icon(
                imageVector = Icons.Outlined.Sort,
                contentDescription = null,
                modifier = Modifier
            )
        }
    }
}