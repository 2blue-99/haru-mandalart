package com.coldblue.network

import io.github.jan.supabase.compose.auth.ComposeAuth

interface SupabaseDataSource {
    val clientToken: String?
    val composeAuth: ComposeAuth


}