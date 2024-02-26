package com.coldblue.data.repo

import android.util.Log
import com.coldblue.datastore.UserDataSource
import com.coldblue.network.SupabaseDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepo {
    override val token: Flow<String> = userDataSource.token
    override val todoUpdateTime: Flow<String> = userDataSource.todoUpdateTime
    override val mandaUpdateTime: Flow<String> = userDataSource.mandaUpdateTime
    override val isTutorial: Flow<Boolean> = userDataSource.isTutorial
    override val isAlarm: Flow<Boolean> = userDataSource.isAlarm
    override val isInit: Flow<Boolean> = userDataSource.isInit

    override suspend fun updateToken(token: String) {
        userDataSource.updateToken(token)
    }

    override suspend fun updateTodoTime(time: String) {
        userDataSource.updateTodoTime(time)
    }

    override suspend fun updateMandaTime(time: String) {
        userDataSource.updateMandaTime(time)
    }

    override suspend fun updateTutorial(state: Boolean) {
        userDataSource.updateTutorial(state)
    }

    override suspend fun updateAlarm(state: Boolean) {
        userDataSource.updateAlarm(state)
    }

    override suspend fun updateInit(state: Boolean) {
        userDataSource.updateInit(state)
    }

}