package com.coldblue.mandalart.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.colddelight.mandalart.R

@Composable
fun UpdateDialog(
    updateTime: String,
    updateContent: String,
    onDismiss: () -> Unit,
    onUpdate: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(HMColor.Background)
            .padding(30.dp)
            .heightIn(
                max = 400.dp,
            ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                modifier = Modifier
                    .clickable { onDismiss() }
                    .size(30.dp),
                contentDescription = ""
            )
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.update_title),
                    style = HmStyle.text18,
                    fontWeight = FontWeight.Bold,
                    color = HMColor.Primary,
                )
                Text(
                    text = updateTime,
                    style = HmStyle.text10,
                    color = HMColor.SubText
                )
                HorizontalDivider(
                    color = HMColor.SubText
                )
            }

            Text(
                text = stringResource(id = R.string.update_dialog_content),
                style = HmStyle.text18,
                fontWeight = FontWeight.Bold,
                color = HMColor.Primary,
            )

            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .background(HMColor.WhitePrimary)
                    .heightIn(
                        min = 150.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    item {
                        Text(
                            text = updateContent.ifEmpty { "내용이 비었어요.." },
                            style = HmStyle.text10,
                            color = HMColor.Text,
                        )
                    }
                }
            }

            HMButton(
                text = stringResource(id = R.string.update_dialog_button),
                clickableState = true
            ) {
                onUpdate()
            }
        }
    }
}


@Preview
@Composable
fun PreviewUpdateDialog() {
    UpdateDialog("시간", "내용", onDismiss = {}, onUpdate = {})
}