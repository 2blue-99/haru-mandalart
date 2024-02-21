package com.coldblue.data.util

import com.coldblue.datastore.UserPreferencesDataSource
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
): LoginHelper {
    override suspend fun getUserLoginState(): Boolean {
        return userPreferencesDataSource.isUserLogin()
    }
}