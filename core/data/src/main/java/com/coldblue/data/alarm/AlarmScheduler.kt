package com.coldblue.data.alarm

import com.coldblue.model.AlarmItem

interface AlarmScheduler {

    fun schedule(item: AlarmItem)

    fun cancel(item: AlarmItem)

    fun reset()
}