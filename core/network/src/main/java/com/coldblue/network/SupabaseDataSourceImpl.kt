package com.coldblue.network

import com.coldblue.network.model.MandaModel
import com.coldblue.network.model.NetworkTodo
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class SupabaseDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : SupabaseDataSource {
    override val clientToken: String? get() = client.auth.currentAccessTokenOrNull()
    override val composeAuth: ComposeAuth = client.composeAuth

}