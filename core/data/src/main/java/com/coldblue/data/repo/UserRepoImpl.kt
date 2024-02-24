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
): UserRepo {
    override val token: Flow<String>
        get() = userDataSource.token
    override val todoUpdateTime: Flow<String>
        get() = userDataSource.todoUpdateTime
    override val mandaUpdateTime: Flow<String>
        get() = userDataSource.mandaUpdateTime
    override val isTutorial: Flow<Boolean>
        get() = userDataSource.isTutorial
    override val isAlarm: Flow<Boolean>
        get() = userDataSource.isAlarm

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

}