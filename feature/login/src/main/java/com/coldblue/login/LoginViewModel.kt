package com.coldblue.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.auth.GetComposeAuthUseCase
import com.coldblue.domain.auth.LoginSucceededUseCase
import com.coldblue.domain.auth.LoginWithOutAuthUseCase
import com.coldblue.domain.network.GetNetworkStateUseCase
import com.coldblue.domain.user.GetExplainStateUseCase
import com.coldblue.login.exception.exceptionHandler
import com.coldblue.login.state.LoginExceptionState
import com.coldblue.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithOutAuthUseCase: LoginWithOutAuthUseCase,
    private val getComposeAuthUseCase: GetComposeAuthUseCase,
    private val loginSucceededUseCase: LoginSucceededUseCase,
    getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getExplainStateUseCase: GetExplainStateUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.None)
    val loginState: StateFlow<LoginUiState> get() = _loginState

    val isOnline: StateFlow<Boolean> = getNetworkStateUseCase().map {
        it
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    var _explainState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val explainState: StateFlow<Boolean> get() = _explainState
//    val explainState: StateFlow<Boolean> = getExplainStateUseCase().map {
//        it
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = false
//    )

    fun getComposeAuth() = getComposeAuthUseCase()

    fun checkLoginState(result: NativeSignInResult) {

        when (result) {
            is NativeSignInResult.Success -> {
                _loginState.value = LoginUiState.Success
                viewModelScope.launch {
                    if(getExplainStateUseCase().first())
                        loginSucceededUseCase()
                    else
                        _explainState.value = true
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


    fun loginWithOutAuth() {
        viewModelScope.launch {
            loginWithOutAuthUseCase()
        }
    }
}