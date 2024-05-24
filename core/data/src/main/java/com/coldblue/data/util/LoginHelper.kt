package com.coldblue.data.util

import io.github.jan.supabase.compose.auth.ComposeAuth
import kotlinx.coroutines.flow.Flow

interface LoginHelper {
    val isLogin: Flow<LoginState>

    fun getComposeAuth(): ComposeAuth
    suspend fun loginWithOutAuth()
    suspend fun login()
    suspend fun logout()
    suspend fun deleteUser()
    suspend fun updateAlarmState(state: Boolean)
}