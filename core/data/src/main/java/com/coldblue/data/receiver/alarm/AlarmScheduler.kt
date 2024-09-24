package com.coldblue.data.receiver.alarm

import com.coldblue.model.NotificationAlarmItem
import java.util.Date


interface AlarmScheduler {
    fun addAlarm(alarmItem: NotificationAlarmItem)
    fun cancelAlarm(alarmCode: Int)
}