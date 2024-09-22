package com.blue.alarm

import androidx.lifecycle.ViewModel
import com.coldblue.data.util.AlarmHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor (
    private val alarmHelper: AlarmHelper
): ViewModel() {
    fun addAlarm(time: Date, alarmCode : Int, content: String){
        alarmHelper.addAlarm(time, alarmCode, content)
    }

    fun cancelAlarm(alarmCode: Int){
        alarmHelper.cancelAlarm(alarmCode)
    }
}