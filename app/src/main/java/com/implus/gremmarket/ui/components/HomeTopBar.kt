package com.implus.gremmarket.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar (
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onAccountProfilePictureClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = colors,
        title = {

        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                        .clip(CircleShape)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .requiredSize(68.dp)
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = 8.dp)
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_notifications),
                        contentDescription = "Показать уведомления"
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_pic_poppy),
                        contentDescription = "Информация об аккаунте",
                        modifier = Modifier
                            .fillMaxSize(0.8f)
                            .clip(CircleShape)
                    )
                }
            }
        }
    )
}

val PlayGreen = Color(0xFF01875F)
val PlayBackground = Color(0xFFF8F9FA)
val TextPrimary = Color(0xFF1F1F1F)
val TextSecondary = Color(0xFF5F6368)
val RatingStarColor = Color(0xFF01875F)