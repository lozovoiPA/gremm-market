package com.implus.gremmarket.ui.account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.visible
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.implus.gremmarket.R
import com.implus.gremmarket.googleSansFontFamily
import com.implus.gremmarket.ui.Destination

@Composable
fun AccountTopBar(
    account: Account,
    scrollBehavior: TopAppBarScrollBehavior,
    scrollState: Boolean,
    navController: NavController
) {
    TopAppBar(
        modifier = Modifier
            .heightIn(max = 90.dp),
        navigationIcon = {
            // Для того, чтобы текст был по центру
            IconButton(onClick = {}, modifier = Modifier.visible(false)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Black
                )
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val density = LocalDensity.current
                val gap = with(density) { 20.dp.toPx() }
                var centerWidthPx by remember { mutableIntStateOf(0) }
                Box(
                    modifier = Modifier
                        .offset { IntOffset(x = -((centerWidthPx / 2) + gap.toInt()), y = 0) }
                ) {
                    AnimatedVisibility(
                        visible = scrollState,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Avatar(account = account, size = 24.dp)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = account.email,
                        style = MaterialTheme.typography.labelLarge,
                        fontFamily = googleSansFontFamily,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.1.sp,
                        modifier = Modifier
                            .onGloballyPositioned {
                                centerWidthPx = it.size.width
                            }
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate(Destination.HOME_GAMES.route) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.AccountBackground),
            scrolledContainerColor = colorResource(R.color.AccountBackground)
        ),
        scrollBehavior = scrollBehavior
    )
}