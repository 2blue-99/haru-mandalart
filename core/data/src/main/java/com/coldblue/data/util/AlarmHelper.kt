package com.coldblue.data.util

import java.util.Date


interface AlarmHelper {
    fun addAlarm(time: Date, alarmCode : Int, content: String)
    fun cancelAlarm(alarmCode: Int)
}