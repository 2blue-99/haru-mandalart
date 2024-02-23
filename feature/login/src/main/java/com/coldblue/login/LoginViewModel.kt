package com.coldblue.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginState
import com.coldblue.domain.user.UpdateUserTokenUseCase
import com.coldblue.login.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val updateUserTokenUseCase: UpdateUserTokenUseCase,
    private val loginHelper: LoginHelper
): ViewModel() {

    private val _loginState = MutableStateFlow(UiState.None)
    val loginState: StateFlow<UiState> get() = _loginState

    fun getComposeAuth(): ComposeAuth = loginHelper.getComposeAuth()
    fun checkLoginState(result: NativeSignInResult){
        when(result){
            NativeSignInResult.Success -> {
                updateToken()
                _loginState.value = UiState.Success
            }
            else -> _loginState.value = UiState.Fail
        }
    }
    private fun updateToken(){
        viewModelScope.launch {
            updateUserTokenUseCase(loginHelper.getClientToken())
        }
    }
}