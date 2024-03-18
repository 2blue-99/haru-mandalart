package com.coldblue.data.repository.user

import com.coldblue.datastore.UserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override val token: Flow<String> = userDataSource.token
    override val email: Flow<String> = userDataSource.email
    override val isTutorial: Flow<Boolean> = userDataSource.isTutorial
    override val isAlarm: Flow<Boolean> = userDataSource.isAlarm
    override val isInit: Flow<Boolean> = userDataSource.mandaInitState

    override suspend fun updateToken(token: String) {
        userDataSource.updateToken(token)
    }

    override suspend fun updateEmail(email: String) {
        userDataSource.updateEmail(email)
    }

    override suspend fun updateTutorial(state: Boolean) {
        userDataSource.updateTutorial(state)
    }

    override suspend fun updateAlarm(state: Boolean) {
        userDataSource.updateAlarm(state)
    }

    override suspend fun updateMandaInitState(state: Boolean) {
        userDataSource.updateMandaInitState(state)
    }

}