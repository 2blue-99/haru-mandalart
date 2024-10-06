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
    topText: String = "",
    targetText: String,
    bottomText: String,
    tintColor: Color,
    subText: String = "",
    confirmText: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
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
                        append(topText)
                        withStyle(
                            style = SpanStyle(
                                color = tintColor,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(targetText)
                        }
                        append(bottomText)
                    }
                )
                if (subText != "")
                    Text(
                        text = subText,
                        style = HmStyle.text12,
                        color = HMColor.SubLightText
                    )
            }
        }, onDismissRequest = {
            onDismissRequest()
        }, confirmButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = HMColor.Background,
                    contentColor = tintColor
                ),
                onClick = {
                    onConfirm()
                }) {
                Text(
                    text = confirmText,
                    color = tintColor,
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
    HMTextDialog("탑 텍스트", "강조", "기모띠", HMColor.Primary, "aa", "확인", {}, {})
}