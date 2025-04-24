package com.coldblue.datastore

import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    val token: Flow<String>
    val isStarted: Flow<Boolean>
    val email: Flow<String>
    val isExplain: Flow<Boolean>
    val isAlarm: Flow<Boolean>
    val mandaInitState: Flow<Boolean>
    val noteRequestDate: Flow<String>
    val currentMandaIndex: Flow<Int>

    suspend fun updateToken(token: String)
    suspend fun updateStarted(state: Boolean)
    suspend fun updateEmail(email: String)
    suspend fun updateExplain(state: Boolean)
    suspend fun updateAlarm(state: Boolean)
    suspend fun updateMandaInitState(state: Boolean)
    suspend fun updateNoteRequestDate(date: String)
    suspend fun updateCurrentMandaIndex(index: Int)
    suspend fun reset()
}