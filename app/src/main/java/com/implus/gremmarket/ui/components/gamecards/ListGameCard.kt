package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.ui.components.RatingStarColor
import com.implus.gremmarket.ui.components.TextPrimary
import com.implus.gremmarket.ui.components.TextSecondary

@Composable
fun ListGameCard(title: String, description: String, size: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Иконка
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(64.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.VideogameAsset, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = TextPrimary
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = TextSecondary,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "4,5", fontSize = 11.sp, color = TextSecondary)
                Icon(Icons.Filled.Star, contentDescription = null, tint = RatingStarColor, modifier = Modifier.size(12.dp))
                Text(text = " • $size", fontSize = 11.sp, color = TextSecondary)
            }
        }
    }
}
