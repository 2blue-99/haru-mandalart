package com.coldblue.data.alarm

import com.coldblue.model.AlarmItem

interface AlarmScheduler {

    fun add(item: AlarmItem)

    fun cancel(id: Int)

    fun reset()
}