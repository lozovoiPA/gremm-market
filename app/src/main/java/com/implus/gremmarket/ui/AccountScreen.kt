package com.implus.gremmarket.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AccountScreen() {
    Scaffold(
        containerColor = Color.Gray
    ) {
        contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding) // account for automatically calculated Scaffold padding (top bar, etc)
                .consumeWindowInsets(contentPadding) // prevent children from accounting for that padding as well
                .windowInsetsPadding( // add padding for content based on system UI for horizontal sides (padding at top already included vertical one)
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        )
        {
            Text("This is an account screen.")
        }
    }
}