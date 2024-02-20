package com.coldblue.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {
    val userToken: Flow<String>
    val todoUpdateTime: Flow<String>
    val mandaUpdateTime: Flow<String>

    suspend fun updateToken(token: String)
    suspend fun updateTodoTime(time: String)
    suspend fun updateMandaTime(time: String)
}