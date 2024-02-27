package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun TitleText(text: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(end = 10.dp),
            text = text,
            style = HmStyle.headline,
            color = HMColor.Primary
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(top=2.dp),
            color = HMColor.Primary,
            thickness = 2.dp
        )
    }
}

@Composable
fun CenterTitleText(text: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.32F).padding(top=2.dp),
            color = HMColor.Primary,
            thickness = 2.dp
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = text,
            style = HmStyle.headline,
            color = HMColor.Primary
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(top=2.dp),
            color = HMColor.Primary,
            thickness = 2.dp
        )
    }
}

@Preview
@Composable
fun TitleTextPreView() {
    TitleText("오늘 할 일")
}

@Preview
@Composable
fun CenterTitleTextPreView() {
    CenterTitleText("하루,만다라트")
}


