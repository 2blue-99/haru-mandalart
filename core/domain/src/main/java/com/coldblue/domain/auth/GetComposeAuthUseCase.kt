package com.coldblue.domain.auth

import com.coldblue.data.util.LoginHelper
import javax.inject.Inject


class GetComposeAuthUseCase @Inject constructor(
    private val loginHelper: LoginHelper,
) {
    operator fun invoke() = loginHelper.getComposeAuth()
}

