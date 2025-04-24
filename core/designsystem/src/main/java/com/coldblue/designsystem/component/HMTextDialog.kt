package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.designsystem.theme.pretendard

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
    canCancel: Boolean = true
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
                                fontSize = 16.sp,
                                fontFamily = pretendard
                            )
                        ){
                            append(topText)
                        }
                        withStyle(
                            style = SpanStyle(
                                color = tintColor,
                                fontSize = 16.sp,
                                fontFamily = pretendard
                            )
                        ) {
                            append(targetText)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontFamily = pretendard
                            )
                        ){
                            append(bottomText)
                        }
                    }
                )
                if (subText != "")
                    Text(
                        text = subText,
                        style = HmStyle.text16,
                        color = HMColor.SubDarkText
                    )
            }
        }, onDismissRequest = {
            onDismissRequest()
        }, confirmButton = {
            Button(
                modifier = Modifier.offset(y = (10.dp)),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = tintColor,
                    contentColor = tintColor
                ),
                onClick = {
                    onConfirm()
                }) {
                Text(
                    text = confirmText,
                    color = Color.White,
                    style = HmStyle.text16
                )
            }
        }, dismissButton = {
            if (canCancel){
                TextButton(
                    modifier = Modifier.offset(y = 10.dp),
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
                        style = HmStyle.text16
                    )
                }
            }
        })
}

@Preview
@Composable
fun DeleteDialogPreview() {
    HMTextDialog("탑 텍스트", "강조", "기모띠", HMColor.Primary, "aa", "확인", {}, {})
}