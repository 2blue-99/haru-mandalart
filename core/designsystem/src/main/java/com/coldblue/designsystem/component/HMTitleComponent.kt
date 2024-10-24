package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.Arrangement
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
fun HMTitleComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(color = HMColor.Primary, modifier = Modifier.weight(1f))
        Text(
            text = "하루 만다라트",
            style = HmStyle.text20,
            modifier = Modifier.padding(horizontal = 15.dp),
            color = HMColor.Primary
        )
        HorizontalDivider(color = HMColor.Primary, modifier = Modifier.weight(1f))
    }
}
@Preview
@Composable
fun HMTitleComponentPreview(){
    HMTitleComponent()
}