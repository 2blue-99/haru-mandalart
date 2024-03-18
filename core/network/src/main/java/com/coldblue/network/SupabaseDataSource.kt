package com.coldblue.network

import io.github.jan.supabase.compose.auth.ComposeAuth
import kotlinx.coroutines.flow.Flow

interface SupabaseDataSource {

    suspend fun deleteUser()

    suspend fun refresh(token:String):Flow<Boolean>

}