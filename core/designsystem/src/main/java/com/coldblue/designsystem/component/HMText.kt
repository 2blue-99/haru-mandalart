package com.coldblue.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.designsystem.theme.orbit

@Composable
fun HMText(
    topText: String,
    targetText: String,
    bottomText: String,
    tintColor: Color,
    fontSize: TextUnit,
) {
    Text(
        buildAnnotatedString {
            append(topText)
            withStyle(
                style = SpanStyle(
                    color = tintColor,
                    fontWeight = FontWeight.Bold,
//                    fontSize = fontSize,
                    fontFamily = orbit
                )
            ){
                append(targetText)
            }
            append(bottomText)
        },
        fontSize = fontSize
    )
}