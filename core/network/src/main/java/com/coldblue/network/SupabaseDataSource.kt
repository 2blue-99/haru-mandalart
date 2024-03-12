package com.coldblue.network

import com.coldblue.network.model.MandaModel
import com.coldblue.network.model.NetworkTodo
import io.github.jan.supabase.compose.auth.ComposeAuth

interface SupabaseDataSource {
    val clientToken: String?
    val composeAuth: ComposeAuth


}