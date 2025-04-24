package com.coldblue.data.repository.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val token: Flow<String>
    val email: Flow<String>
    val isExplain: Flow<Boolean>
    val isAlarm: Flow<Boolean>
    val isInit: Flow<Boolean>
    val currentMandaIndex: Flow<Int>
    val noteRequestDate: Flow<String>


    suspend fun updateToken(token: String)
    suspend fun updateEmail(email: String)
    suspend fun updateExplain(state: Boolean)
    suspend fun updateAlarm(state: Boolean):Boolean
    suspend fun updateMandaInitState(state: Boolean)
    suspend fun updateNoteRequestDate(date: String)
    suspend fun updateCurrentMandaIndex(index: Int)
    suspend fun refresh()
}