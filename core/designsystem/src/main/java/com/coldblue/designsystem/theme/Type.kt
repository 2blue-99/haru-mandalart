package com.coldblue.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.coldblue.designsystem.R

val pretendard = FontFamily(
    Font(R.font.pretendard_regular)
)

val typography = Typography(
    headlineLarge = TextStyle(fontFamily = pretendard),
    headlineMedium = TextStyle(fontFamily = pretendard),
    headlineSmall = TextStyle(fontFamily = pretendard),
    bodyLarge = TextStyle(fontFamily = pretendard),
    bodyMedium = TextStyle(fontFamily = pretendard),
    bodySmall = TextStyle(fontFamily = pretendard),
    labelLarge = TextStyle(fontFamily = pretendard),
    labelMedium = TextStyle(fontFamily = pretendard),
    labelSmall = TextStyle(fontFamily = pretendard),
    titleLarge = TextStyle(fontFamily = pretendard),
    titleMedium = TextStyle(fontFamily = pretendard),
    titleSmall = TextStyle(fontFamily = pretendard),
    displayLarge = TextStyle(fontFamily = pretendard),
    displayMedium = TextStyle(fontFamily = pretendard),
    displaySmall = TextStyle(fontFamily = pretendard),
)

object HmStyle{
    val text60 = TextStyle(
        fontFamily = pretendard,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold,
    )
    val text46 = TextStyle(
        fontFamily = pretendard,
        fontSize = 46.sp,
        fontWeight = FontWeight.Bold,
    )
    val text40 = TextStyle(
        fontFamily = pretendard,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
    )
    val text30 = TextStyle(
        fontFamily = pretendard,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
    val text24 = TextStyle(
        fontFamily = pretendard,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    val text20 = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    val text18 = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
    )
    val text16 = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
    )
    val text14 = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
    )
    val text12 = TextStyle(
        fontFamily = pretendard,
        fontSize = 12.sp,
    )
    val text10 = TextStyle(
        fontFamily = pretendard,
        fontSize = 10.sp,
    )
    val text8 = TextStyle(
        fontFamily = pretendard,
        fontSize = 8.sp,
    )
    val text6 = TextStyle(
        fontFamily = pretendard,
        fontSize = 6.sp,
    )

    val text4 = TextStyle(
        fontFamily = pretendard,
        fontSize = 4.sp,
    )
}



