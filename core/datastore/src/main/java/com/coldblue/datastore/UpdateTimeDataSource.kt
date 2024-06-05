package com.coldblue.datastore

import kotlinx.coroutines.flow.Flow

interface UpdateTimeDataSource {
    val todoUpdateTime: Flow<String>
    val mandaKeyUpdateTime: Flow<String>
    val mandaDetailUpdateTime: Flow<String>

    suspend fun setTodoUpdateTime(time: String)
    suspend fun setMandaKeyUpdateTime(time: String)
    suspend fun setMandaDetailUpdateTime(time: String)
    suspend fun reset()
}