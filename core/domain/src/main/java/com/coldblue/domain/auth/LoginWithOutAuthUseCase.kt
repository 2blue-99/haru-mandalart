package com.coldblue.domain.auth

import com.coldblue.data.util.LoginHelper
import javax.inject.Inject

class LoginWithOutAuthUseCase @Inject constructor(
    private val loginHelper: LoginHelper,
) {
    suspend operator fun invoke() = loginHelper.loginWithOutAuth()
}

