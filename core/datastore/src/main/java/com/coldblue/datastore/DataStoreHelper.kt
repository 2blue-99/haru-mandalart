package com.coldblue.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {
    suspend fun updateToken(token: String)
    suspend fun updateTodoTime(time: String)
    suspend fun updateMandaTime(time: String)
    suspend fun updateTutorial(state: Boolean)
    suspend fun updateAlarm(state: Boolean)
}