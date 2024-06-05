package com.coldblue.data.repository.user

import com.coldblue.data.util.SettingHelper
import com.coldblue.datastore.UserDataSource
import com.coldblue.network.SupabaseDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val supabaseDataSource: SupabaseDataSource,
    private val settingHelper: SettingHelper,
) : UserRepository {
    override val token: Flow<String> = userDataSource.token
    override val email: Flow<String> = userDataSource.email
    override val isExplain: Flow<Boolean> = userDataSource.isExplain
    override val isAlarm: Flow<Boolean> = userDataSource.isAlarm
    override val isInit: Flow<Boolean> = userDataSource.mandaInitState
    override val noteRequestDate: Flow<String> = userDataSource.noteRequestDate

    override suspend fun updateToken(token: String) {
        userDataSource.updateToken(token)
    }

    override suspend fun updateEmail(email: String) {
        userDataSource.updateEmail(email)
    }

    override suspend fun updateExplain(state: Boolean) {
        userDataSource.updateExplain(state)
    }

    override suspend fun updateAlarm(state: Boolean): Boolean {
        if (settingHelper.checkAlarmPermission()) {
            userDataSource.updateAlarm(state)
            return true
        } else {
            return false
        }
    }

    override suspend fun updateMandaInitState(state: Boolean) {
        userDataSource.updateMandaInitState(state)
    }

    override suspend fun updateNoteRequestDate(date: String) {
        userDataSource.updateNoteRequestDate(date)
    }

    override suspend fun refresh() {
        userDataSource.token.flatMapLatest {
            supabaseDataSource.refresh(it)
        }
    }

}