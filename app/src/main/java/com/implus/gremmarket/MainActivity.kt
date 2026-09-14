package com.implus.gremmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.implus.gremmarket.ui.HomeScreen


class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge( // turn off accounting for system tray and navigation
        )
        setContent {
            HomeScreen()
        }
    }
}