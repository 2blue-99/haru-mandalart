package com.coldblue.data.util

import com.coldblue.datastore.UserDataSource
import com.coldblue.network.SupabaseDataSource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val supabaseDataSource: SupabaseDataSource
) : LoginHelper {
    override val isLogin: Flow<Boolean> =
        userDataSource.token.map { it != "" }

    override val composeAuth: ComposeAuth =
        supabaseDataSource.composeAuth
}