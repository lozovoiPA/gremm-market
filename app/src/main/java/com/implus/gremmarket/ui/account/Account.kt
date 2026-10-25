package com.implus.gremmarket.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Account(
    val name: String,
    val email: String,
    val color: Color,
    val profilePicture: Painter? = null
)

@Composable
fun Avatar(account: Account, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(account.color),
        contentAlignment = Alignment.Center
    ) {
        if(account.profilePicture != null) {
            Image(
                painter = account.profilePicture,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        } else {
            Text(
                text = account.name.firstOrNull()?.uppercase() ?: "",
                color = Color.White,
                style = if (size > 40.dp) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.titleSmall
            )
        }

    }
}