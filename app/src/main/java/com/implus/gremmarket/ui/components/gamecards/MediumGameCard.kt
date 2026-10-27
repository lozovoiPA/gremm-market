package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.components.AppBadgeRow
import com.implus.gremmarket.ui.components.AppIcon
import com.implus.gremmarket.ui.deviceapps.AppInfo

@Composable
fun MediumGameCard(app: AppInfo) {
    Column(
        modifier = Modifier
            .width(110.dp)
            .padding(end = 12.dp)
            .clickable(
                onClick = {},
                indication = ripple(color = colorResource(R.color.ClickRippleGray)),
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            )
    ) {
        // Иконка
        AppIcon(app, 100)

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.widthIn(max = 100.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = app.name,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = (MaterialTheme.typography.bodyMedium.fontSize.value - 1).sp,
                maxLines = 2,
                lineHeight = (MaterialTheme.typography.bodyMedium.lineHeight.value - 3).sp,
                overflow = TextOverflow.Ellipsis
            )
            //Spacer(modifier = Modifier.height(4.dp))
            AppBadgeRow(app)
        }
    }
}