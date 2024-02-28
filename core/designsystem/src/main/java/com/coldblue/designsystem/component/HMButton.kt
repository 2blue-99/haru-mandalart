package com.coldblue.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun HMButton(
    text: String,
    clickableState: Boolean,
    onClick: () -> Unit
) {

    val animatedButtonColor = animateColorAsState(
        targetValue = if (clickableState) HMColor.Primary else HMColor.Gray,
        animationSpec = tween(150, 0, LinearEasing), label = ""
    )

    Button(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(10.dp),
        border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = Color.Transparent),
        colors = ButtonDefaults.buttonColors(
            contentColor = animatedButtonColor.value,
            disabledContainerColor = animatedButtonColor.value
        ),
        onClick = { onClick() },
        enabled = clickableState
    ){
        Text(text = text, color = HMColor.Background ,style = HmStyle.text16, fontWeight = FontWeight.Bold)
    }
}
@Preview
@Composable
fun HMButtonPreview(){
    HMButton("hello", false){}
}