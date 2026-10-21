package com.implus.gremmarket

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.implus.gremmarket.ui.AccountScreenContent
import com.implus.gremmarket.ui.AccountScreenScaffold
import com.implus.gremmarket.ui.AppNavHost
import com.implus.gremmarket.ui.Destination
import com.implus.gremmarket.ui.deviceapps.DeviceAppsGeneralContent
import com.implus.gremmarket.ui.deviceapps.DeviceAppsManagementContent
import com.implus.gremmarket.ui.deviceapps.DeviceAppsScreenScaffold

val LocalNavigation = staticCompositionLocalOf<NavHostController> { error("Not provided") }
val DeviceAppsLocalNavigation = staticCompositionLocalOf<NavHostController> { error("Not provided") }

class MainActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Позволяет рисовать от системных треев (сверху, снизу, сбоку)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.setNavigationBarContrastEnforced(false)
        } // По умолчанию системный навбар будет изменять цвет чтобы контрастировать с экраном. Это приводит к тому, что он не прозрачный. Эта функция убирает автоматический контраст

        val appTypography = getAppTypography()
        setContent {
            MaterialTheme(
                typography = appTypography
            ) {
                val navController = rememberNavController();
                CompositionLocalProvider(
                    LocalNavigation provides navController
                ) {
                    AppNavHost(
                        navController = navController,
                        startDestination = Destination.DEVICEAPPS
                    )
                }
            }
        }
    }
}

// Эта функция нужна для получения текущей активности, а из нее - окна, поскольку на разных экранах разное
// поведение (цвет) системной панели навигации
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@Composable
fun DeviceAppsScreen() {
    val view = LocalView.current
    val activity = view.context.findActivity()
    val window = activity?.window

    val systemNavBarColor = colorResource(R.color.DeviceAppsBottomBarBlue)

    SideEffect {
        window?.let {
            it.navigationBarColor = systemNavBarColor.toArgb()
        }
    }

    val navController = rememberNavController()

    CompositionLocalProvider(
        DeviceAppsLocalNavigation provides navController
    ) {
        SystemSurface() { modifier ->
            DeviceAppsScreenScaffold(
                modifier = modifier
            ) { contentPadding, innerModifier ->
                NavHost(
                    navController = navController,
                    route = Destination.DEVICEAPPS.route,
                    startDestination = Destination.DEVICEAPPS_APPS.route
                ) {
                    composable(Destination.DEVICEAPPS_GENERAL.route) {
                        DeviceAppsGeneralContent(contentPadding, innerModifier)
                    }
                    composable(Destination.DEVICEAPPS_APPS.route) {
                        DeviceAppsManagementContent(contentPadding, innerModifier)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountScreen() {
    val view = LocalView.current
    val activity = view.context.findActivity()
    val window = activity?.window

    SideEffect {
        window?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                it.navigationBarColor = Color.Transparent.toArgb()
            }
        }
    }

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