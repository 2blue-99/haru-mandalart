package com.coldblue.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Plus
import com.coldblue.designsystem.theme.HMColor

@Composable
fun HMMandaEmptyButton() {
    val stroke = Stroke(
        width = 10f,
        pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(5f, 10f), phase = 0f)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1F)
            .clip(RoundedCornerShape(8.dp))
            .clickable {  }
            .drawBehind {
                drawRoundRect(
                    color = Color.Gray,
                    style = stroke,
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
    ){
        Icon(
            modifier = Modifier.fillMaxSize().scale(0.35f),
            imageVector = IconPack.Plus,
            tint = Color.Gray,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun HMMandaEmptyPreview() {
    HMMandaEmptyButton()
}