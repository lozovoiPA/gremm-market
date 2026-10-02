package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.ui.components.RatingStarColor
import com.implus.gremmarket.ui.components.TextPrimary
import com.implus.gremmarket.ui.components.TextSecondary

@Composable
fun SmallGameCard(title: String, rating: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), // Квадратная иконка
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.SportsEsports, contentDescription = null, tint = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = TextPrimary
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = rating, fontSize = 10.sp, color = TextSecondary)
            Icon(Icons.Filled.Star, contentDescription = null, tint = RatingStarColor, modifier = Modifier.size(10.dp))
        }
    }
}