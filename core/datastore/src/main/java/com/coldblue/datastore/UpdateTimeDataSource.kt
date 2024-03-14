package com.coldblue.datastore

import kotlinx.coroutines.flow.Flow

interface UpdateTimeDataSource {
    val todoUpdateTime: Flow<String>
    val todoGroupUpdateTime: Flow<String>
    val currentGroupUpdateTime: Flow<String>
    val mandaKeyUpdateTime: Flow<String>
    val mandaDetailUpdateTime: Flow<String>

    suspend fun setTodoUpdateTime(time: String)
    suspend fun setTodoGroupUpdateTime(time: String)
    suspend fun setCurrentGroupUpdateTime(time: String)
    suspend fun setMandaKeyUpdateTime(time: String)
    suspend fun setMandaDetailUpdateTime(time: String)
}