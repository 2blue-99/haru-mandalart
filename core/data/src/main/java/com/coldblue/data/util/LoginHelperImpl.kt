package com.coldblue.data.util

import android.util.Log
import com.coldblue.datastore.UserDataSource
import com.coldblue.network.SupabaseDataSource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val supabaseDataSource: SupabaseDataSource
) : LoginHelper {
    override val isLogin: Flow<LoginState> =
        userDataSource.token.map {
            if(it.isBlank())
                LoginState.Logout
            else
                LoginState.Login
        }

    override fun getComposeAuth(): ComposeAuth =
        supabaseDataSource.composeAuth

    override fun getClientToken(): String =
        supabaseDataSource.composeAuth.supabaseClient.auth.currentAccessTokenOrNull() ?: ""
}