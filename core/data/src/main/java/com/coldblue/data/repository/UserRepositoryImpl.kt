package com.coldblue.data.repository

import com.coldblue.datastore.UserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override val token: Flow<String> = userDataSource.token
    override val isTutorial: Flow<Boolean> = userDataSource.isTutorial
    override val isAlarm: Flow<Boolean> = userDataSource.isAlarm
    override val isInit: Flow<Boolean> = userDataSource.isInit

    override suspend fun updateToken(token: String) {
        userDataSource.updateToken(token)
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