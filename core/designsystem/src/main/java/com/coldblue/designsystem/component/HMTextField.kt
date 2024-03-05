package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.coldblue.designsystem.theme.HMColor

@Composable
fun HMTextField(
    inputText: String = "",
    onChangeText: (String) -> Unit
) {
    var text by remember { mutableStateOf(inputText) }
    LaunchedEffect(inputText){
        text = inputText
    }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
            onChangeText(text)
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = HMColor.Primary,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun Preview() {
    HMTextField {}
}
