package com.implus.gremmarket

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

val PlayGreen = Color(0xFF01875F)
val PlayBackground = Color(0xFFF8F9FA)
val TextPrimary = Color(0xFF1F1F1F)
val TextSecondary = Color(0xFF5F6368)
val RatingStarColor = Color(0xFF01875F)

val googleFontProvider by lazy {
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}
val googleSansFont by lazy { GoogleFont("Google Sans") }
val googleSansFontFamily by lazy { FontFamily(
    Font(googleFont = googleSansFont, fontProvider = googleFontProvider, weight = FontWeight.Normal),
    Font(googleFont = googleSansFont, fontProvider = googleFontProvider, weight = FontWeight.Bold)
) }

val badgeSmall by lazy { TextStyle(
    fontFamily = googleSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp
) }

fun getAppTypography(): Typography {
    val appFontFamily = FontFamily.SansSerif
    val appFontFamilyFancy = googleSansFontFamily

    val appTypography = Typography(

        displayLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp
        ),
        displayMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.sp
        ),
        displaySmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp
        ),


        headlineLarge = TextStyle(
            fontFamily = appFontFamilyFancy,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = appFontFamilyFancy,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = appFontFamilyFancy,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),


        titleLarge = TextStyle(
            fontFamily = appFontFamilyFancy,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = appFontFamilyFancy,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        titleSmall = TextStyle(
            fontFamily = appFontFamilyFancy,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),


        bodyLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.1.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),


        labelLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            letterSpacing = 0.em,
            lineHeight = 18.sp,
            fontWeight = FontWeight.W500,
            fontSize = 15.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.em,
            fontSize = 14.sp,
            lineHeight = 16.sp
        )
        /*
        labelMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp
        )
         */
    )
    return appTypography
}