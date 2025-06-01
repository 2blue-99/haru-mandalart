package com.coldblue.mandalart.screen

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
import androidx.compose.ui.window.Dialog
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.UpdateNote
import com.colddelight.mandalart.R

@Composable
fun UpdateDialog(
    updateNote: UpdateNote,
    onDismiss: () -> Unit,
    onUpdate: () -> Unit
) {

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .heightIn(max = 600.dp)
                .background(HMColor.Background)
                .padding(30.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.background(HMColor.Background),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_title),
                        style = HmStyle.text18,
                        fontWeight = FontWeight.Bold,
                        color = HMColor.Primary,
                    )
                    Text(
                        text = updateNote.updateTime,
                        style = HmStyle.text12,
                        color = HMColor.SubLightText
                    )
                    HorizontalDivider(
                        color = HMColor.SubLightText
                    )
                }

                Text(
                    text = stringResource(id = R.string.update_note_dialog_content),
                    style = HmStyle.text16,
                    fontWeight = FontWeight.Bold,
                    color = HMColor.Text,
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
                                text = updateNote.updateContent.ifEmpty { "내용이 비었어요.." },
                                style = HmStyle.text12,
                                color = HMColor.Text,
                            )
                        }
                    }
                }

                HMButton(
                    text = stringResource(id = R.string.update_note_dialog_button),
                    clickableState = true
                ) {
                    onUpdate()
                }
            }

            Box(
                contentAlignment = Alignment.TopEnd,
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .size(30.dp),
                    tint = HMColor.Primary,
                    contentDescription = " "
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewUpdateDialog() {
    UpdateDialog(UpdateNote("",""), onDismiss = {}, onUpdate = {})
}