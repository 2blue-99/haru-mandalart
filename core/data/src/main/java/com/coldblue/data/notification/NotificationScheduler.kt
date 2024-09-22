package com.coldblue.data.notification

import com.coldblue.model.AlarmItem

interface NotificationScheduler {

    suspend fun add(item: AlarmItem)

    suspend fun cancel(id: Int)

    suspend fun cancelAll()
}