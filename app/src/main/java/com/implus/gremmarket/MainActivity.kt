package com.implus.gremmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.implus.gremmarket.ui.AppNavHost

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Позволяет рисовать от системных треев (сверху, снизу, сбоку)
        setContent {
            AppNavHost()
        }
    }
}