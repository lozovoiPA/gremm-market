package com.implus.gremmarket.ui.components.gamecards

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.ui.components.RatingStarColor
import com.implus.gremmarket.ui.components.TextPrimary
import com.implus.gremmarket.ui.components.TextSecondary

@Composable
fun BigGameCard(
    title: String,
    rating: String,
    size: String,
    imageRes: Int // Замените на реальный ID ресурса или используйте Coil для URL
) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .padding(end = 12.dp)
    ) {
        // Скриншот
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            // Заглушка для изображения. В реальном проекте используйте AsyncImage (Coil)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Image, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.White)
                // Image(painter = painterResource(id = imageRes), contentDescription = null, contentScale = ContentScale.Crop)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Информация об игре
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Иконка
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = TextPrimary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Ролевые игры", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Рейтинг и размер
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = rating, fontSize = 12.sp, color = TextPrimary)
            Icon(Icons.Filled.Star, contentDescription = null, tint = RatingStarColor, modifier = Modifier.size(14.dp).padding(start = 2.dp))
            Text(text = " • $size", fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(start = 4.dp))
        }
    }
}