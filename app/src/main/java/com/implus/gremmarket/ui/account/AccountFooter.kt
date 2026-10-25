package com.implus.gremmarket.ui.account

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.TextSecondary

@Composable
fun AccountFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Фиксируем текущую ориентацию экрана при ее изменении.
        var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
        val configuration = LocalConfiguration.current

        LaunchedEffect(configuration) {
            snapshotFlow { configuration.orientation }
                .collect { orientation = it }
        }

        // Расположение текста меняется в зависимости от ориентации экрана.
        // Альбомная - в одну строку. Портретная - в две строки
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                AccountFooterContentHorizontal()
            }
            else -> {
                AccountFooterContentVertical()
            }
        }
    }
}

@Composable
fun AccountFooterContentHorizontal() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Политика конфиденциальности",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
        Spacer(Modifier.padding(start = 14.dp))
        Box(
            modifier = Modifier
                .size(3.dp)
                .clip(CircleShape)
                .background(TextSecondary)
        )
        Spacer(Modifier.padding(start = 14.dp))
        Text("Условия использования",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
    }

}

@Composable
fun AccountFooterContentVertical() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Политика конфиденциальности",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.padding(10.dp))
        Box(
            modifier = Modifier
                .size(3.dp)
                .clip(CircleShape)
                .background(TextSecondary)
        )
        Spacer(Modifier.padding(9.dp))
        Text(
            text = "Условия использования",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
    }
}