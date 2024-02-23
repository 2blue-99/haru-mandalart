package com.coldblue.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick:() -> Unit
){
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.DarkGray),
        onClick = { onClick() },
        shape = RoundedCornerShape(5.dp),
        modifier = modifier.fillMaxWidth().height(60.dp)
    ) {
        Row {
//            Icon(painter = , contentDescription = ) TODO 구글 아이콘 추가 예정
            Text(
                color = Color.DarkGray,
                text = "Sign in with Google"
            )
        }
    }
}


@Preview
@Composable
fun Preview(){
    LoginButton {

    }
}