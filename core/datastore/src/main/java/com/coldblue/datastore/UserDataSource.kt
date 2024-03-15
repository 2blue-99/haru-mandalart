package com.coldblue.datastore

import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    val token: Flow<String>
    val email: Flow<String>
    val isTutorial: Flow<Boolean>
    val isAlarm: Flow<Boolean>
    val isInit: Flow<Boolean>

    suspend fun updateToken(token: String)
    suspend fun updateEmail(email: String)
    suspend fun updateTutorial(state: Boolean)
    suspend fun updateAlarm(state: Boolean)
    suspend fun updateInit(state: Boolean)

    suspend fun reset()
}