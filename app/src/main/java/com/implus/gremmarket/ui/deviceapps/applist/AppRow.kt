package com.implus.gremmarket.ui.deviceapps.applist

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Badge
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary
import com.implus.gremmarket.ui.components.ActionIcon
import com.implus.gremmarket.ui.deviceapps.AppDeviceFilter
import com.implus.gremmarket.ui.deviceapps.AppInfo
import com.implus.gremmarket.ui.deviceapps.DeviceAppsViewState
import com.implus.gremmarket.ui.deviceapps.DownloadProgress
import com.implus.gremmarket.ui.deviceapps.DownloadState
import kotlin.math.max

@Composable
fun AppRow(
    appId: Int,
    app: AppInfo,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    viewState: DeviceAppsViewState
) {
    val downloadState = viewState.activeDownloads[appId]

    val cornerRadius = if (downloadState != null)
        7.dp else 14.dp
    val shadow =
        Shadow(
            radius = 2.dp,
            spread = 0.dp,
            color = colorResource(R.color.Shadow),
            offset = DpOffset(x = (-1).dp, 1.dp)
        )
    val containerSize = if (downloadState != null)
        21.dp else 42.dp
    val iconFontSize = if (downloadState != null)
        14.sp else 21.sp


    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .padding(start = 4.dp)
            .heightIn(min=60.dp)
            .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
            .background(if (checked) colorResource(R.color.AccountBackground) else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(42.dp),
            contentAlignment = Alignment.Center
        ) {
            if(viewState.appDeviceFilter == AppDeviceFilter.NOT_INSTALLED && downloadState != null) {
                AppDownloadIndication(downloadState)
            }
            Box(
                Modifier
                    .size(containerSize)
                    .dropShadow(
                        shape = RoundedCornerShape(cornerRadius),
                        shadow = shadow
                    )
                    .background(color = app.iconBgColor, shape = RoundedCornerShape(cornerRadius)),
                contentAlignment = Alignment.Center
            ) {
                Text(app.icon, fontSize = iconFontSize)
            }
        }



        Spacer(Modifier.width(11.dp))
        Column(
            Modifier
                .weight(1f)
                .padding(vertical = 6.dp)
        ) {
            Text(
                app.name,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (downloadState != null) {
                val badgeText = when(downloadState.downloadState) {
                    DownloadState.RETRIEVAL -> "Подождите..."
                    DownloadState.DOWNLOAD -> "${(max(downloadState.progress * 100, 1f)).toInt()}% из ${app.size}"
                    DownloadState.INSTALLING -> "Установка..."
                    else -> "Подождите..."
                }
                Spacer(modifier = Modifier.height(4.dp))
                Badge(
                    containerColor = colorResource(R.color.DeviceAppsBottomBarBlue)
                ) {
                    Text(text = badgeText, style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp))
                }
            } else {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(app.size, maxLines = 1, style = MaterialTheme.typography.labelMedium)

                    if(app.lastUpdated.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .clip(CircleShape)
                                .background(TextSecondary)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text("Обновлено ${app.lastUpdated}", maxLines = 1, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

        }
        if (downloadState == null) {
            Spacer(modifier = Modifier.width(15.dp))
            ActionIcon(
                icon = Icons.Default.ExpandMore,
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = { },
                        indication = ripple(color = colorResource(R.color.ClickRippleGray)),
                        interactionSource = remember { MutableInteractionSource() },
                        role = Role.Button
                    )
            )
            Spacer(modifier = Modifier.width(20.dp))
            Checkbox(
                checked = checked,
                onCheckedChange = onChecked,
                modifier = Modifier
                    .size(34.dp),
                colors = CheckboxDefaults.colors().copy(
                    checkedBoxColor = colorResource(R.color.MarketBlue),
                    checkedBorderColor = colorResource(R.color.MarketBlue)
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
        } else {

            ActionIcon(
                icon = Icons.Default.Close,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = { },
                        indication = ripple(color = colorResource(R.color.ClickRippleGray)),
                        interactionSource = remember { MutableInteractionSource() },
                        role = Role.Button
                    )
            )
            Spacer(modifier = Modifier.width(22.dp))

        }
    }
}

@Composable
fun AppDownloadIndication(
    downloadState: DownloadProgress
) {
    val targetProgress = max(downloadState.progress, 0.01f)

    /*
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 800),
        label = "DownloadProgressAnimation"
    ) */

    val animatedValue = remember { Animatable(0f) }
    val animateRotation = rememberInfiniteTransition("animateRotation")
    val angle by animateRotation.animateFloat(
        0f,
        360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(downloadState.downloadState) {
        animatedValue.snapTo(0.0f)
    }

    LaunchedEffect(targetProgress) {
        animatedValue.animateTo(
            targetValue = targetProgress,
            animationSpec = tween(durationMillis = 800)
        )
    }

    when(downloadState.downloadState) {
        DownloadState.RETRIEVAL, DownloadState.INSTALLING -> {
            CircularWavyProgressIndicator(
                progress = { animatedValue.value },
                modifier = Modifier
                    .size(56.dp)
                    .offset(y = 2.dp)
                    .rotate(angle),
                color = colorResource(R.color.MarketBlue),
                trackColor = colorResource(R.color.MarketAccentBlue),
                stroke = Stroke(6f),
                trackStroke = Stroke(6f),
                amplitude = { 0f }
            )
        }
        DownloadState.DOWNLOAD -> {
            CircularWavyProgressIndicator(
                progress = { animatedValue.value },
                modifier = Modifier
                    .size(56.dp)
                    .offset(y = 2.dp),
                color = colorResource(R.color.MarketBlue),
                trackColor = colorResource(R.color.MarketAccentBlue),
                stroke = Stroke(6f),
                trackStroke = Stroke(6f)
            )
        }
        else -> {}
    }

}