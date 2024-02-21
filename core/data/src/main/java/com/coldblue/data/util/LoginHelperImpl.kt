package com.coldblue.data.util

import com.coldblue.datastore.UserPreferencesDataStore
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
): LoginHelper {
    override suspend fun getUserLoginState(): Boolean {
        return userPreferencesDataStore.isUserLogin()
    }
}