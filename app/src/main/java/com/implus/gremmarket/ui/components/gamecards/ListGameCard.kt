package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.TextSecondary
import com.implus.gremmarket.ui.components.AppBadgeRow
import com.implus.gremmarket.ui.components.AppIcon
import com.implus.gremmarket.ui.deviceapps.AppInfo

@Composable
fun ListGameCard(app: AppInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, end=16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIcon(app, 60)

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier
            .widthIn(max = 250.dp)
            .offset(y = 10.dp)
        ) {
            Text(
                text = app.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = app.shortDescription,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = TextSecondary,
                lineHeight = 15.sp,
                letterSpacing = 0.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            AppBadgeRow(app, 8.dp)
        }
    }
}
