package com.coldblue.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.alarm.AlarmSchedulerImpl
import com.coldblue.model.AlarmItem
import java.time.LocalDateTime

@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
) {
    var secondText by remember {
        mutableStateOf("")
    }
    var msg by remember {
        mutableStateOf("")
    }
    var alarmItem: AlarmItem? = null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = secondText,
            onValueChange = { secondText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "알람시간") }
        )
        OutlinedTextField(
            value = msg,
            onValueChange = { msg = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "메세지 내용 ") }
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                alarmItem = AlarmItem(
                    LocalDateTime.now().plusSeconds(secondText.toLong()),
                    msg,
                    1234
                )
                alarmItem?.let(settingViewModel::schedule)
                secondText = ""
                msg = ""
            }) {
                Text(text = "알람 추가하기")
            }
            Button(onClick = { alarmItem?.let(settingViewModel::cancel) }) {
                Text(text = "알람 취소하기")
            }

        }


    }

}