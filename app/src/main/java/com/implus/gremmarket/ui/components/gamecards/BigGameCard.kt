package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.badgeMedium
import com.implus.gremmarket.ui.components.AppBadgeRow
import com.implus.gremmarket.ui.components.AppIcon
import com.implus.gremmarket.ui.components.CircleDivider
import com.implus.gremmarket.ui.deviceapps.AppInfo

@Composable
fun BigGameCard(
    app: AppInfo
) {
    val shadow =
        Shadow(
            radius = 3.dp,
            spread = 0.dp,
            color = colorResource(R.color.Shadow),
            offset = DpOffset(x = (0).dp, 2.dp)
        )

    Column(
        modifier = Modifier
            .width(220.dp)
            .padding(end = 12.dp)
            .clickable(
                onClick = {},
                indication = ripple(color = colorResource(R.color.ClickRippleGray)),
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            )
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .dropShadow(
                    shape = RoundedCornerShape(12.dp),
                    shadow = shadow)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                //Icon(Icons.Filled.Image, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.White)
                if(app.bannerImageId != 0) {
                    Image(
                        painter = painterResource(id = app.bannerImageId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize())
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Информация об игре
        Row() {
            // Иконка
            AppIcon(app, 60)
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp, bottom = 6.dp).offset(y = 3.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(app.genre.isNotEmpty()) {
                        Text(text = app.genre, style = badgeMedium, maxLines = 1)
                        if(app.subgenre.isNotEmpty()) {
                            CircleDivider()
                            Text(
                                text = app.subgenre,
                                style = badgeMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                }
                AppBadgeRow(app)
            }
        }
    }
}