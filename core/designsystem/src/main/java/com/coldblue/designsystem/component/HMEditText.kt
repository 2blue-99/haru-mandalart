package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.coldblue.designsystem.theme.HMColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HMEditText(
    text: String = ""
) {
    var inputText by remember { mutableStateOf(text) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = inputText,
        onValueChange = { inputText = it },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = HMColor.Primary,
            containerColor = Color.Transparent
        ),
    )

}

@Preview
@Composable
fun Preview() {
    HMEditText()
}
