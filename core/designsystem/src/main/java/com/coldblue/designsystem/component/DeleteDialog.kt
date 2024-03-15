package com.coldblue.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor

@Composable
fun DeleteDialog(
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
                text = text,
                fontWeight = FontWeight.Bold
            )
        }, onDismissRequest = {
            onDismissRequest()
        }, confirmButton = {
            TextButton(onClick = {
                onConfirmation()
            }) {
                Text(
                    text = deleteConfirmText,
                    color = HMColor.Dark.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }, dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text("취소", fontWeight = FontWeight.Bold, color = HMColor.Text)
            }
        })
}

@Preview
@Composable
fun DeleteDialogPreview() {
    DeleteDialog("탈퇴하면 모든 데이터가 완전히 삭제됩니다.", "탈퇴", {}, {})
}