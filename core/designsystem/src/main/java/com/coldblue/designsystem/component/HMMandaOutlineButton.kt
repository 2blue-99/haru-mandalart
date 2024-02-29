package com.coldblue.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun HMMandaOutlineButton(
    name: String,
    outlineColor: Color,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1F)
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        OutlinedButton(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, outlineColor),
            onClick = { onClick() }
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = name,
                modifier = Modifier.padding(5.dp),
                style = HmStyle.text12
            )
        }
    }
}

@Preview
@Composable
fun HMMandaOutlineButtonPreview() {
    HMMandaOutlineButton("hello", HMColor.Primary) {}
}