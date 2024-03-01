package com.coldblue.designsystem.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun HMMandaOutlineButton(
    name: String,
    outlineColor: Color,
    onClick: () -> Unit
) {
    Log.e("TAG", "HMMandaOutlineButton: $name")
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1F)
            .border(BorderStroke(2.dp, outlineColor),RoundedCornerShape(8.dp))
            .background(HMColor.Background)
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            textAlign = TextAlign.Center,
            color = outlineColor,
            text = name,
            style = HmStyle.text6
        )
    }
}

@Preview
@Composable
fun HMMandaOutlineButtonPreview() {
    HMMandaOutlineButton("hello", HMColor.Primary) {}
}