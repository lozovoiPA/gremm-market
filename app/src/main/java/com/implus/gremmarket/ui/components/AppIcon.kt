package com.implus.gremmarket.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary
import com.implus.gremmarket.badgeSmall
import com.implus.gremmarket.ui.deviceapps.AppInfo

@Composable
fun AppIcon(
    app: AppInfo,
    containerSize: Int = 42,
) {
    val shadow =
        Shadow(
            radius = 3.dp,
            spread = 0.dp,
            color = colorResource(R.color.AccountBackground),
            offset = DpOffset(x = (-1).dp, 3.dp)
        )

    Box(
        Modifier
            .size(containerSize.dp)
            .dropShadow(
                shape = RoundedCornerShape(30),
                shadow = shadow
            )
            .background(color = app.iconBgColor, shape = RoundedCornerShape(30)),
        contentAlignment = Alignment.Center
    ) {
        if(app.iconImageId != 0) {
            Image(
                painter = painterResource(id = app.iconImageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(30)))
        } else {
            Text(app.icon, fontSize = (containerSize * 0.3).sp)
        }

    }
}

@Composable
fun CircleDivider() {
    Box(
        modifier = Modifier.size(10.dp).width(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(3.dp)
                .clip(CircleShape)
                .background(TextSecondary)
        )
    }
}

@Composable
fun ContentBadge(
    content: @Composable ((Modifier) -> Unit) = {}
) {
    val modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp)
    Badge(
        containerColor = colorResource(R.color.DeviceAppsBottomBarBlue),
        modifier = Modifier.padding(end = 4.dp)
    ) {
        content(modifier)
    }
}

@Composable
fun AppBadgeRow(app: AppInfo, badgeGap: Dp = 0.dp) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 2.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(badgeGap)
        ) {
        if(app.rating.isNotEmpty()) {
            ContentBadge() { modifier ->
                Row(
                    modifier = modifier.padding(end = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = app.rating, style = badgeSmall)
                    Icon(Icons.Filled.Star, contentDescription = null, tint = colorResource(R.color.MarketBlue), modifier = Modifier.size(12.dp).padding(start = 2.dp))
                }
            }
        }
        if(app.price.isNotEmpty()) {
            ContentBadge() { modifier ->
                Text(text = app.price, style = badgeSmall, modifier = modifier)
            }
        }
        if(app.size.isNotEmpty()) {
            Text(text = app.size, style = badgeSmall, overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}