package com.coldblue.designsystem.component

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HMChip(
    tag: String = "TEST",
){
    AssistChip(
        onClick = {  },
        label = {
            Text(tag)
        },
    )
}

@Preview
@Composable
fun Preview2(){
    HMChip(tag = "hello")
}