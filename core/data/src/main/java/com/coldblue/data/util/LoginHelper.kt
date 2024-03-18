package com.coldblue.data.util

import io.github.jan.supabase.compose.auth.ComposeAuth
import kotlinx.coroutines.flow.Flow

interface LoginHelper {
    val isLogin: Flow<LoginState>
    val initPermissionState: Flow<Boolean>
    fun getComposeAuth(): ComposeAuth
    suspend fun setLoginSucceeded()
    suspend fun setLogout()
    suspend fun deleteUser()
    suspend fun updatePermissionInitState(state: Boolean)
}