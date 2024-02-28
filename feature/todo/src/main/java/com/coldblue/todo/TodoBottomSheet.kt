package com.coldblue.todo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoBottomSheet() {
    var onSwitch by remember { mutableStateOf(false) }
    var onDetail by remember { mutableStateOf(false) }
    var todoText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }

    Column(
    ) {
        Text(text = "할 일", style = HmStyle.text16, fontWeight = FontWeight.Bold)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = todoText,
            onValueChange = {
                todoText = it
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = HMColor.Primary,
                containerColor = Color.Transparent
            ),
        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = "시간",
            style = HmStyle.text16,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "시간없음")
            Switch(checked = onSwitch, onCheckedChange = { onSwitch = !onSwitch })
        }
        if (!onDetail) {
            ClickableText(
                modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                text = AnnotatedString("세부설정"),
                style = TextStyle(color = HMColor.Gray, textAlign = TextAlign.End),
                onClick = { onDetail = true })
        }


        if (onDetail) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "설명",
                style = HmStyle.text16,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = contentText,
                onValueChange = {
                    contentText = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = HMColor.Primary,
                    containerColor = Color.Transparent
                ),
            )
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "날짜",
                style = HmStyle.text16,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(top = 48.dp),
                text = "그룹",
                style = HmStyle.text16,
                fontWeight = FontWeight.Bold
            )


        }


    }
}