package com.coldblue.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.NetworkHelper
import com.coldblue.login.exception.exceptionHandler
import com.coldblue.login.state.LoginExceptionState
import com.coldblue.login.state.LoginUiState
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
class LoginViewModel @Inject constructor(
    private val loginHelper: LoginHelper,
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
}