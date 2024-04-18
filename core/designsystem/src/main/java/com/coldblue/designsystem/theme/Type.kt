package com.coldblue.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.coldblue.designsystem.R

val orbit = FontFamily(
    Font(R.font.orbit)
)

val typography = Typography(
    headlineLarge = TextStyle(fontFamily = orbit),
    headlineMedium = TextStyle(fontFamily = orbit),
    headlineSmall = TextStyle(fontFamily = orbit),
    bodyLarge = TextStyle(fontFamily = orbit),
    bodyMedium = TextStyle(fontFamily = orbit),
    bodySmall = TextStyle(fontFamily = orbit),
    labelLarge = TextStyle(fontFamily = orbit),
    labelMedium = TextStyle(fontFamily = orbit),
    labelSmall = TextStyle(fontFamily = orbit),
    titleLarge = TextStyle(fontFamily = orbit),
    titleMedium = TextStyle(fontFamily = orbit),
    titleSmall = TextStyle(fontFamily = orbit),
    displayLarge = TextStyle(fontFamily = orbit),
    displayMedium = TextStyle(fontFamily = orbit),
    displaySmall = TextStyle(fontFamily = orbit),
)

object HmStyle{
    val text46 = TextStyle(
        fontFamily = orbit,
        fontSize = 46.sp,
        fontWeight = FontWeight.Bold,
    )
    val text30 = TextStyle(
        fontFamily = orbit,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
    val text24 = TextStyle(
        fontFamily = orbit,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    val text20 = TextStyle(
        fontFamily = orbit,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    val text18 = TextStyle(
        fontFamily = orbit,
        fontSize = 18.sp,
    )
    val text16 = TextStyle(
        fontFamily = orbit,
        fontSize = 16.sp,
    )
    val text12 = TextStyle(
        fontFamily = orbit,
        fontSize = 12.sp,
    )
    val text10 = TextStyle(
        fontFamily = orbit,
        fontSize = 10.sp,
    )
    val text8 = TextStyle(
        fontFamily = orbit,
        fontSize = 8.sp,
    )
    val text6 = TextStyle(
        fontFamily = orbit,
        fontSize = 6.sp,
    )

    val text4 = TextStyle(
        fontFamily = orbit,
        fontSize = 4.sp,
    )
}



