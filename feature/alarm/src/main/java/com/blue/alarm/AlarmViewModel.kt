package com.blue.alarm

import androidx.lifecycle.ViewModel
import com.coldblue.data.alarm.AlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor (
    private val alarmScheduler: AlarmScheduler
): ViewModel() {
    fun addAlarm(time: Date, alarmCode : Int, content: String){
        alarmScheduler.addAlarm(time, alarmCode, content)
    }

    fun cancelAlarm(alarmCode: Int){
        alarmScheduler.cancelAlarm(alarmCode)
    }
}