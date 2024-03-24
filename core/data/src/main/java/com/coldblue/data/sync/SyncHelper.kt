package com.coldblue.data.sync

import com.coldblue.database.entity.SyncableEntity

interface SyncHelper {
    suspend fun syncWrite()

    fun initialize()

    suspend fun reset()
    suspend fun setMaxUpdateTime(data: List<SyncableEntity>,updateTime: suspend (String)->Unit)
}