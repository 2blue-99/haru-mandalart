package com.coldblue.mandalart.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Plus
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun MandaKeyBox(
    name: String,
    color: Color,
    isDone: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .aspectRatio(1F)
            .border(BorderStroke(1.5.dp, color), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(if (isDone) color else HMColor.Background)
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            textAlign = TextAlign.Center,
            color = if (isDone) HMColor.Background else color,
            text = name,
            style = HmStyle.text6,
        )
    }
}

@Composable
fun MandaDetailBox(
    name: String,
    darkColor: Color,
    lightColor: Color,
    isDone: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .aspectRatio(1F)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isDone) darkColor else lightColor)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = name,
            color = if (isDone) HMColor.Background else HMColor.Text,
            modifier = Modifier.padding(5.dp),
            style = HmStyle.text6,
        )
    }
}

@Composable
fun MandaEmptyBox(
    color: Color = HMColor.SubLightText,
    onClick: () -> Unit
) {
    val stroke = Stroke(
        width = 6f,
        pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(15f, 15f), phase = 0f)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .aspectRatio(1F)
            .border(1.dp, HMColor.Gray, RoundedCornerShape(8.dp))
//            .clip(RoundedCornerShape(8.dp))
//            .drawBehind {
//                drawRoundRect(
//                    color = color,
//                    style = stroke,
//                    cornerRadius = CornerRadius(8.dp.toPx())
//                )
//            }
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .scale(0.35f),
            imageVector = IconPack.Plus,
            tint = HMColor.SubLightText,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun DetailBoxPreview() {
    MandaEmptyBox() {}
}