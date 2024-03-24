package com.coldblue.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginState
import com.coldblue.data.util.NetworkHelper
import com.coldblue.data.util.SettingHelper
import com.coldblue.domain.user.GetAlarmStateUseCase
import com.coldblue.domain.user.GetEmailUseCase
import com.coldblue.domain.user.UpdateAlarmStateUseCase
import com.coldblue.setting.exception.exceptionHandler
import com.coldblue.setting.state.LoginExceptionState
import com.coldblue.setting.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingHelper: SettingHelper,
    private val loginHelper: LoginHelper,
    getEmailUseCase: GetEmailUseCase,
    getAlarmStateUseCase: GetAlarmStateUseCase,
    private val updateAlarmStateUseCase: UpdateAlarmStateUseCase,
    networkHelper: NetworkHelper

) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.None)
    val loginState: StateFlow<LoginUiState> get() = _loginState
    val isOnline: StateFlow<Boolean> = networkHelper.isOnline.map {
        it
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    val loginWithOutAuth: StateFlow<LoginState> = loginHelper.isLogin.map {
        it
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LoginState.Loading
    )
    val versionName = settingHelper.versionName

    val email = getEmailUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
    )
    val alarm = getAlarmStateUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )


    fun getComposeAuth(): ComposeAuth = loginHelper.getComposeAuth()


    fun checkLoginState(result: NativeSignInResult) {
        when (result) {
            is NativeSignInResult.Success -> {
                _loginState.value = LoginUiState.Success
                viewModelScope.launch {
                    loginHelper.setLoginSucceeded()
                }
            }
            is NativeSignInResult.Error -> {
                when (result.message.exceptionHandler()) {
                    is LoginExceptionState.Waiting -> _loginState.value =
                        LoginUiState.Fail(LoginExceptionState.Waiting())

                    is LoginExceptionState.Unknown -> _loginState.value =
                        LoginUiState.Fail(LoginExceptionState.Unknown(result.message))

                    else -> {}
                }
            }

            else -> {}
        }
    }
    fun logout() {
        viewModelScope.launch {
            loginHelper.setLogout()
        }
    }
    fun login() {
        viewModelScope.launch {
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            loginHelper.deleteUser()
        }
    }

    fun showOss() {
        settingHelper.showOss()
    }

    fun showContact() {
        settingHelper.showContact()
    }

    fun showPlayStore() {
        settingHelper.showPlayStore()
    }

    fun updateAlarmState(state: Boolean) {
        viewModelScope.launch {
            updateAlarmStateUseCase(state)
        }
    }
}