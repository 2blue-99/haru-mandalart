package com.coldblue.domain.auth

import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAuthStateUseCase @Inject constructor(
    private val loginHelper: LoginHelper,
) {
    operator fun invoke(): Flow<LoginState> = loginHelper.isLogin
}

