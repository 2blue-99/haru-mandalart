package com.coldblue.data.util

import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.sync.SyncHelper
import com.coldblue.database.dao.AppDao
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.datastore.UserDataSource
import com.coldblue.network.SupabaseDataSource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val updateTimeDataSource: UpdateTimeDataSource,
    private val client: SupabaseClient,
    private val alarmScheduler: AlarmScheduler,
    private val appDao: AppDao,
    private val syncHelper: SyncHelper,
    private val supabaseDataSource: SupabaseDataSource
) : LoginHelper {
    override val isLogin: Flow<LoginState> =
        userDataSource.token.map {
            if (it.isBlank())
                LoginState.Logout
            else
                LoginState.Login
        }

    override fun getComposeAuth(): ComposeAuth = client.composeAuth

    override suspend fun setLoginSucceeded() {
        userDataSource.updateToken(client.auth.currentAccessTokenOrNull() ?: "")
        userDataSource.updateEmail(client.auth.currentUserOrNull()?.email ?: "")
    }

    override suspend fun setLogout() {
        client.auth.clearSession()
        alarmScheduler.cancelAll()
        userDataSource.reset()
        updateTimeDataSource.reset()
        appDao.reset()
        syncHelper.reset()
    }

    override suspend fun deleteUser() {
        supabaseDataSource.deleteUser()
        setLogout()
    }
}