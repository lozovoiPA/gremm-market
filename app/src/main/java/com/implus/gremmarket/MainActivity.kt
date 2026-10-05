package com.implus.gremmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import com.implus.gremmarket.ui.AccountScreenContent
import com.implus.gremmarket.ui.AccountScreenScaffold

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Позволяет рисовать от системных треев (сверху, снизу, сбоку)
        window.setNavigationBarContrastEnforced(false) // По умолчанию системный навбар будет изменять цвет чтобы контрастировать с экраном. Это приводит к тому, что он не прозрачный. Эта функция убирает автоматический контраст

        setContent {
            //AppNavHost()
            AccountScreen()
        }
    }
}

@Composable
fun AccountScreen() {
    SystemSurface() { modifier ->
        AccountScreenScaffold(
            modifier = modifier
        ) { contentPadding, innerModifier ->
            AccountScreenContent(contentPadding, innerModifier)
        }
    }
}

@Composable
fun SystemSurface(
    content: @Composable ((Modifier) -> Unit) = { }
) {
    Surface( // "Системная" поверхность, отвечающая за паддинги и цветах на системных треях в т.ч. и при повороте экрана.
        color = colorResource(R.color.MarketSystemArea),
        modifier = Modifier
            .fillMaxSize()
    ) {
        val modifier = Modifier // Сдвиг элементов относительно системных треев и на ЧЕРНУЮ ПОЛОСУ СЛЕВА (появляется при повороте экрана - СМ. Google Play)
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Left
                )
            )
        content(modifier)
    }
}