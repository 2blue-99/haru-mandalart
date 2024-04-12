package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun HMTextDialog(
    targetText: String,
    text: String,
    tintColors: Color,
    subText: String = "",
    confirmText: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        containerColor = HMColor.Background,
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = tintColors,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(targetText)
                        }
                        append(text)
                    }
                )
                if (subText != "")
                    Text(
                        text = subText,
                        style = HmStyle.text12,
                        color = HMColor.SubText
                    )
            }
        }, onDismissRequest = {
            onDismissRequest()
        }, confirmButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = HMColor.Background,
                    contentColor = tintColors
                ),
                onClick = {
                    onConfirmation()
                }) {
                Text(
                    text = confirmText,
                    color = tintColors,
                    fontWeight = FontWeight.Bold,
                    style = HmStyle.text16
                )
            }
        }, dismissButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = HMColor.Background,
                    contentColor = HMColor.Text
                ),
                onClick = {
                    onDismissRequest()
                }) {
                Text(
                    text = "취소",
                    color = HMColor.Text,
                    fontWeight = FontWeight.Bold,
                    style = HmStyle.text16
                )
            }
        })
}

@Preview
@Composable
fun DeleteDialogPreview() {
//    HMTextDialog("ff", "탈퇴하면 모든 데이터가 완전히 삭제됩니다.", "탈퇴", HMColor.Dark.Red, "aa", {}, {})
}