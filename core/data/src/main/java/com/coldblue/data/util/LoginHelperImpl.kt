package com.coldblue.data.util

import com.coldblue.datastore.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : LoginHelper {
    override val isLogin: Flow<Boolean> =
        userDataSource.token.map { it != "" }
}