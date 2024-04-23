package com.coldblue.domain.auth

import com.coldblue.data.util.LoginHelper
import javax.inject.Inject


class GetAuthStateUseCase @Inject constructor(
    private val loginHelper: LoginHelper,
) {
    operator fun invoke() = loginHelper.isLogin
}

