package com.coldblue.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor

@Composable
fun DeleteDialog(
    targetText: String,
    text: String,
    deleteConfirmText: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        containerColor = HMColor.Background,
        text = {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = HMColor.Dark.Red,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(targetText)
                    }
                    append(text)
                }
            )
//            Text(
//                text = text,
//                fontWeight = FontWeight.Bold
//            )
        }, onDismissRequest = {
            onDismissRequest()
        }, confirmButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = HMColor.Background,
                    contentColor = HMColor.Dark.Red
                ),
                onClick = {
                    onConfirmation()
                }) {
                Text(
                    text = deleteConfirmText,
                    color = HMColor.Dark.Red,
                    fontWeight = FontWeight.Bold
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
                Text("취소", fontWeight = FontWeight.Bold, color = HMColor.Text)
            }
        })
}

@Preview
@Composable
fun DeleteDialogPreview() {
    DeleteDialog("ff", "탈퇴하면 모든 데이터가 완전히 삭제됩니다.", "탈퇴", {}, {})
}