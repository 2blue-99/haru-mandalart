package com.coldblue.designsystem.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = HMColor.Primary,
    secondary = HMColor.Primary,
    tertiary = HMColor.Primary,
    primaryContainer = Color(red = 227, green = 221, blue = 255, alpha = 255), //시간 컨테이너
    tertiaryContainer =  Color(red = 227, green = 221, blue = 255, alpha = 255),//오전 오후
    background = HMColor.Background
)

@Composable
fun HarumandalartTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = LightColorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}