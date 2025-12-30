package com.example.ykiosk_android_test.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    //primary = Purple40,
    //secondary = PurpleGrey40,
    tertiary = Pink40,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
    primary = YKioskColors.Primary,
    onPrimary = Color.White, // 오렌지색 버튼 위의 글자색
    primaryContainer = YKioskColors.ScreenBackground,

    secondary = YKioskColors.Secondary,
    onSecondary = YKioskColors.TextPrimary,

    background = YKioskColors.ScreenBackground,
    onBackground = YKioskColors.TextPrimary,

    surface = YKioskColors.CardSurface,
    onSurface = YKioskColors.InputField,
    onSurfaceVariant = YKioskColors.SidebarBackground, // 사이드바용
    tertiaryContainer = YKioskColors.ToastBackground,
    onTertiaryContainer = YKioskColors.Primary,
    outline = YKioskColors.TextSecondary // 구분선 등에 사용
)

@Composable
fun Ykiosk_android_testTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        //typography = Typography,
        content = content
    )
}

val AppTypography = Typography(
    bodySmall = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 16.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    // BodyLarge: 앱의 주된 본문 텍스트 스타일
    bodyLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    // TitleLarge: 제목 텍스트 스타일
    titleLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    )
    // 다른 폰트 스타일(headline, label 등)도 필요에 따라 설정합니다.
)