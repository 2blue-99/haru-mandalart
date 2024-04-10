package com.coldblue.setting.state

import com.coldblue.setting.R

sealed interface LoginUiState {
    data object Success : LoginUiState
    data object None : LoginUiState
    data class Fail(val loginException: LoginExceptionState) : LoginUiState
}

sealed interface LoginExceptionState {
    val msg: String
    data class DropDown(override val msg: String = "") : LoginExceptionState
    data class Waiting(override val msg: String = R.string.waiting.toString()) : LoginExceptionState
    data class Unknown(override val msg: String) : LoginExceptionState
}

