package com.coldblue.designsystem.component

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.coldblue.designsystem.theme.HMColor

@Composable
fun HMChip(
    tag: String,
    onClick: (String) -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }
    AssistChip(
        border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = Color.Transparent),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = HMColor.Box,
            labelColor = HMColor.Text,
        ),
        onClick = {
            onClick(tag)
            isClicked = !isClicked
        },
        label = {
            Text(tag)
        },
    )
}
@Preview
@Composable
fun ChipPreview(){
    HMChip("hello"){}
}