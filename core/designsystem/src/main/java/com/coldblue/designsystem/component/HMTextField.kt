package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun HMTextField(
    inputText: String = "",
    maxLen: Int = -1,
    hint: String = "",
    onChangeText: (String) -> Unit = {},
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(inputText) { text = inputText }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        placeholder = {
            Text(
                text = hint,
                style = HmStyle.text16,
                color = HMColor.DarkGray
            )
        },
        onValueChange = {
            if (maxLen != -1) {
                if (it.length <= maxLen) {
                    text = it
                    onChangeText(text)
                }
            } else {
                text = it
                onChangeText(text)
            }
        },
        supportingText = {
            if (maxLen!=-1){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "${text.length} / $maxLen",
                    style = HmStyle.text12
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = HMColor.Primary,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    )
}

@Preview
@Composable
fun HMTextFieldPreview() {
    HMTextField("아아아", 10, "", {})
}
