package com.coldblue.data.receiver.notification

import com.coldblue.model.NotificationAlarmItem

interface NotificationScheduler {

    suspend fun add(item: NotificationAlarmItem)

    suspend fun cancel(id: Int)

    suspend fun cancelAll()
}