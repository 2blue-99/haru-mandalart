package com.coldblue.mandalart.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.core.view.WindowCompat
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import java.util.logging.Logger

const val MANDA_TEXT_FIELD_MAX_LINE = 3

@Composable
fun MandaTextField(
    inputText: String = "",
    maxLen: Int,
    onChangeText: (String) -> Unit = {}
){
    var text by remember { mutableStateOf("") }
    var currentLine by remember { mutableIntStateOf(1) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(inputText){
        text = inputText
    }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            if (it.length <= maxLen && currentLine <= MANDA_TEXT_FIELD_MAX_LINE) {
                currentLine = it.count { it == '\n' } + 1
                text = it
                onChangeText(text)
            }
        },
        maxLines = 3,
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                text = "${text.length} / $maxLen",
                style = HmStyle.text12
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = HMColor.Primary,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = if(currentLine>=3) ImeAction.Done else ImeAction.Default
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
    )
}

@Preview
@Composable
fun MandaTextFieldPreview() {
    MandaTextField("아아아", 10, {})
}
