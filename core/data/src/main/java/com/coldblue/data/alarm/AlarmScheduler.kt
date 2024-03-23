package com.coldblue.data.alarm

import com.coldblue.model.AlarmItem

interface AlarmScheduler {

    suspend fun add(item: AlarmItem)

    suspend fun cancel(id: Int)

    suspend fun cancelAll()
}