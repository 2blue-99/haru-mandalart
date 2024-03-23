package com.coldblue.designsystem.component

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import com.coldblue.designsystem.theme.HMColor

@Composable
fun HMSwitch(checked: Boolean, onCheckedChange: () -> Unit) {
    Switch(
        colors = SwitchDefaults.colors(
            checkedThumbColor = HMColor.Background,
            uncheckedThumbColor = HMColor.Background,
            uncheckedTrackColor = HMColor.Gray,
            checkedTrackColor = HMColor.Primary,
            uncheckedBorderColor = HMColor.Gray,
            checkedBorderColor = HMColor.Primary,
        ),
        checked = checked,
        onCheckedChange = {
            onCheckedChange()
        })
}