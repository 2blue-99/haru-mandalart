package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.coldblue.designsystem.theme.HMColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HMTextField(
    text: String = "",
    onChangeText: (String) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onChangeText(it) },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = HMColor.Primary,
            unfocusedPlaceholderColor = Color.Transparent,
            focusedPlaceholderColor = Color.Transparent
        ),
    )
}

@Preview
@Composable
fun Preview() {
    HMTextField{}
}
