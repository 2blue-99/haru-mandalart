package com.coldblue.mandalart.screen.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Mandalart
import com.coldblue.designsystem.iconpack.Question
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun MandaTopBar(
    navigateToTutorial: () -> Unit,
    navigateToSetting: () -> Unit,
    onClickDetail: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = IconPack.Mandalart,
                tint = HMColor.Primary,
                contentDescription = "main_icon"
            )
            Text(
                text = "하루 만다라트",
                style = HmStyle.text18,
                modifier = Modifier.padding(horizontal = 15.dp),
                color = HMColor.Primary,
            )
        }
        Row {
            IconButton(
                onClick = { onClickDetail() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.MoreVert,
                    tint = HMColor.Primary,
                    contentDescription = "detail"
                )
            }
            IconButton(
                onClick = { navigateToTutorial() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = IconPack.Question,
                    tint = HMColor.Primary,
                    contentDescription = "question"
                )
            }
            IconButton(
                onClick = { navigateToSetting() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Settings,
                    tint = HMColor.Primary,
                    contentDescription = "setting"
                )
            }
        }
    }
}