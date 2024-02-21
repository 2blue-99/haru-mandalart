package com.coldblue.data.repo

import com.coldblue.datastore.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepoImpl @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
): UserPreferencesRepo {
    override val token: Flow<String>
        get() = userPreferencesDataSource.token
    override val todoUpdateTime: Flow<String>
        get() = userPreferencesDataSource.todoUpdateTime
    override val mandaUpdateTime: Flow<String>
        get() = userPreferencesDataSource.mandaUpdateTime
    override val isTutorial: Flow<Boolean>
        get() = userPreferencesDataSource.isTutorial
    override val isAlarm: Flow<Boolean>
        get() = userPreferencesDataSource.isAlarm

    override suspend fun updateToken(token: String) {
        userPreferencesDataSource.updateToken(token)
    }

    override suspend fun updateTodoTime(time: String) {
        userPreferencesDataSource.updateTodoTime(time)
    }

    override suspend fun updateMandaTime(time: String) {
        userPreferencesDataSource.updateMandaTime(time)
    }

    override suspend fun updateTutorial(state: Boolean) {
        userPreferencesDataSource.updateTutorial(state)
    }

    override suspend fun updateAlarm(state: Boolean) {
        userPreferencesDataSource.updateAlarm(state)
    }

}