package com.coldblue.setting.state

import com.coldblue.data.util.LoginState


sealed interface SettingUIState {
    data object Loading : SettingUIState
    data class Error(val msg: String) : SettingUIState
    data class Success(
        val isOnline: Boolean,
        val loginWithOutAuth: LoginState,
        val email: String,
        val isAlarm: Boolean,
    ) : SettingUIState

}