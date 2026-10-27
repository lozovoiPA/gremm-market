package com.implus.gremmarket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.TextPrimary
import com.implus.gremmarket.TextSecondary

@Composable
fun CategoryHeader(
    title: String,
    displayButton: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontSize = (MaterialTheme.typography.titleLarge.fontSize.value - 4).sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        if(displayButton) {
            Box(
                modifier = Modifier
                    .height(33.dp)
                    .widthIn(28.dp, 30.dp)
                    .padding(end = 2.dp)
                    .clip(RoundedCornerShape(50))
                    .background(colorResource(R.color.DeviceAppsBottomBarBlue)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.widthIn(max = 20.dp))
                }
            }
        }
    }
}