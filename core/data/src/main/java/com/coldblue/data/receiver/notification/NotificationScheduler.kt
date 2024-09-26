package com.coldblue.data.receiver.notification

import com.coldblue.model.NotificationAlarmItem


interface NotificationScheduler {
    suspend fun add(alarmItem: NotificationAlarmItem)
    suspend fun cancel(alarmCode: Int)
    suspend fun cancelAll()
}