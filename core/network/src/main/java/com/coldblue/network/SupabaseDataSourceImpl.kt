package com.coldblue.network

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.gotrue.auth
import javax.inject.Inject

class SupabaseDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : SupabaseDataSource {
    override val clientToken: String? get() = client.auth.currentAccessTokenOrNull()
    override val composeAuth: ComposeAuth = client.composeAuth

}