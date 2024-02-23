package com.coldblue.data.util

import io.github.jan.supabase.compose.auth.ComposeAuth
import kotlinx.coroutines.flow.Flow

interface LoginHelper {
    val isLogin: Flow<LoginState>
    fun getComposeAuth(): ComposeAuth
    fun getClientToken(): String
}