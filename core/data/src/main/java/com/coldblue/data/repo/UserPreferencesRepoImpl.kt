package com.coldblue.data.repo

import com.coldblue.datastore.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepoImpl @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
): UserPreferencesRepo {
    override val token: Flow<String>
        get() = userPreferencesDataStore.token
    override val todoUpdateTime: Flow<String>
        get() = userPreferencesDataStore.todoUpdateTime
    override val mandaUpdateTime: Flow<String>
        get() = userPreferencesDataStore.mandaUpdateTime
    override val isTutorial: Flow<Boolean>
        get() = userPreferencesDataStore.isTutorial
    override val isAlarm: Flow<Boolean>
        get() = userPreferencesDataStore.isAlarm

    override suspend fun updateToken(token: String) {
        userPreferencesDataStore.updateToken(token)
    }

    override suspend fun updateTodoTime(time: String) {
        userPreferencesDataStore.updateTodoTime(time)
    }

    override suspend fun updateMandaTime(time: String) {
        userPreferencesDataStore.updateMandaTime(time)
    }

    override suspend fun updateTutorial(state: Boolean) {
        userPreferencesDataStore.updateTutorial(state)
    }

    override suspend fun updateAlarm(state: Boolean) {
        userPreferencesDataStore.updateAlarm(state)
    }

}