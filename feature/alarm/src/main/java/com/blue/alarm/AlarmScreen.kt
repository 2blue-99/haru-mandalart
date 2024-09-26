package com.blue.alarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blue.alarm.AlarmUtil.dayOfWeekToText
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.colddelight.alarm.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId


@Composable
fun AlarmScreen(
    title: String,
    onFinished: () -> Unit,
    onStartActivity: () -> Unit
){
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.alarm_background),
            contentScale = ContentScale.Crop,
            contentDescription = "background"
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(bottom = 160.dp)
        ) {
            MainContent(
                title = title
            )
        }

        Box(
            contentAlignment = Alignment.BottomCenter
        ){
            BottomContent(
                onFinished = onFinished,
                onStartActivity = onStartActivity
            )
        }
    }
}

@Composable
fun MainContent(
    title: String
){
    val timeFormat = SimpleDateFormat("hh:mm")
    val currentSystemTime = System.currentTimeMillis()
    val formatTime = timeFormat.format(currentSystemTime)
    val currentDate = Instant.ofEpochMilli(currentSystemTime).atZone(ZoneId.systemDefault()).toLocalDate()
    val month = currentDate.monthValue
    val day = currentDate.dayOfMonth
    val today = currentDate.dayOfWeek.value
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatTime,
                style = HmStyle.text60,
                color = Color.White
            )
            Text(
                text = "${month}월 ${day}일 ${today.dayOfWeekToText()}",
                style = HmStyle.text16,
                color = Color.White
            )
        }
        Image(
            painterResource(id = R.drawable.manda_image),
            modifier = Modifier.size(140.dp),
            contentDescription = ""
        )
        Text(
            text = title,
            style = HmStyle.text16,
            color = Color.White
        )
    }
}

@Composable
fun BottomContent(
    onFinished: () -> Unit,
    onStartActivity: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(70.dp)
    ){
        IconButton(
            onClick = onFinished,
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = HMColor.Background)
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.Close,
                tint = HMColor.Primary,
                contentDescription = "close"
            )
        }
        TextButton(onClick = onStartActivity) {
            Text(
                modifier = Modifier.drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - 2.sp.toPx()
                    drawLine(
                        color = HMColor.Background,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                },
                style = HmStyle.text14,
                text = "하루 만다라트 바로가기",
                color = HMColor.Background
            )
        }
    }
}

@Preview
@Composable
fun AlarmPreview(){
    AlarmScreen(
        title = "",
        onFinished = {},
        onStartActivity = {}
    )
}

@Preview
@Composable
fun MainContentPreview(){
    MainContent("")
}

@Preview
@Composable
fun BottomContentPreview(){
    BottomContent(
        onFinished = {},
        onStartActivity = {}
    )
}