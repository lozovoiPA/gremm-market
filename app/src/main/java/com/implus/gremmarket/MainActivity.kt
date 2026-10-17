package com.implus.gremmarket

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
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.implus.gremmarket.ui.AccountScreenContent
import com.implus.gremmarket.ui.AccountScreenScaffold
import com.implus.gremmarket.ui.AppNavHost
import com.implus.gremmarket.ui.Destination
import com.implus.gremmarket.ui.DeviceAppsGeneralContent
import com.implus.gremmarket.ui.DeviceAppsManagementContent
import com.implus.gremmarket.ui.DeviceAppsScreenScaffold

val LocalNavigation = staticCompositionLocalOf<NavHostController> { error("Not provided") }
val DeviceAppsLocalNavigation = staticCompositionLocalOf<NavHostController> { error("Not provided") }

class MainActivity : ComponentActivity(){
    fun getAppTypography(): Typography {
        val provider = GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs
        )

        val robotoGoogleFont = GoogleFont("Roboto")

        val RobotoFamily = FontFamily(
            Font(googleFont = robotoGoogleFont, fontProvider = provider, weight = FontWeight.Normal),
            Font(googleFont = robotoGoogleFont, fontProvider = provider, weight = FontWeight.Bold)
        )

        val appTypography = Typography(

            // ---------- Display ----------
            displayLarge = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 57.sp,
                lineHeight = 64.sp,
                letterSpacing = (-0.25).sp
            ),
            displayMedium = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 45.sp,
                lineHeight = 52.sp,
                letterSpacing = 0.sp
            ),
            displaySmall = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp,
                lineHeight = 44.sp,
                letterSpacing = 0.sp
            ),

            // ---------- Headline ----------
            headlineLarge = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.sp
            ),
            headlineMedium = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.sp
            ),
            headlineSmall = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp
            ),

            // ---------- Title ----------
            titleLarge = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
            ),
            titleMedium = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp
            ),
            titleSmall = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp
            ),

            // ---------- Body ----------
            bodyLarge = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp
            ),
            bodySmall = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp
            ),

            // ---------- Label ----------
            labelLarge = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp
            ),
            labelMedium = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp
            ),
            labelSmall = TextStyle(
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp
            )
        )
        return appTypography;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Позволяет рисовать от системных треев (сверху, снизу, сбоку)
        window.setNavigationBarContrastEnforced(false) // По умолчанию системный навбар будет изменять цвет чтобы контрастировать с экраном. Это приводит к тому, что он не прозрачный. Эта функция убирает автоматический контраст

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

@Composable
fun DeviceAppsScreen() {
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