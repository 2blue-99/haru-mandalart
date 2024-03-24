package com.coldblue.setting.state

sealed interface LoginUiState {
    data object Success : LoginUiState
    data object None : LoginUiState
    data class Fail(val loginException: LoginExceptionState) : LoginUiState
}

sealed interface LoginExceptionState {
    val msg: String
    data class DropDown(override val msg: String = "") : LoginExceptionState
    data class Waiting(override val msg: String = "잠시 후 다시 시도해 주세요.") : LoginExceptionState
    data class Unknown(override val msg: String) : LoginExceptionState
}

