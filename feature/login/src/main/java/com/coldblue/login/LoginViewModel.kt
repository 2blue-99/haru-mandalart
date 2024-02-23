package com.coldblue.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.LoginHelper
import com.coldblue.domain.user.UpdateUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val updateUserTokenUseCase: UpdateUserTokenUseCase,
    private val loginHelper: LoginHelper
): ViewModel() {
    val composeAuth = loginHelper.composeAuth

//    private val _isLogin = MutableStateFlow<Boolean>(false)
//    val isLogin: StateFlow<Boolean> get() = _isLogin

    fun checkLoginState(result: NativeSignInResult){
        when(result){
            NativeSignInResult.Success -> updateToken()
            else -> {  }
        }
    }
    private fun updateToken(){
        viewModelScope.launch {
            updateUserTokenUseCase()
        }
    }
}