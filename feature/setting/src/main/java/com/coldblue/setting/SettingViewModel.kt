package com.coldblue.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginState
import com.coldblue.data.util.NetworkHelper
import com.coldblue.data.util.SettingHelper
import com.coldblue.domain.auth.DeleteUserUseCase
import com.coldblue.domain.auth.GetAuthStateUseCase
import com.coldblue.domain.auth.GetComposeAuthUseCase
import com.coldblue.domain.auth.LoginSucceededUseCase
import com.coldblue.domain.auth.LoginWithOutAuthUseCase
import com.coldblue.domain.auth.LogoutUseCase
import com.coldblue.domain.network.GetNetworkStateUseCase
import com.coldblue.domain.user.GetAlarmStateUseCase
import com.coldblue.domain.user.GetEmailUseCase
import com.coldblue.domain.user.UpdateAlarmStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getComposeAuthUseCase: GetComposeAuthUseCase,
    private val loginSucceededUseCase: LoginSucceededUseCase,
    private val settingHelper: SettingHelper,
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    getEmailUseCase: GetEmailUseCase,
    getAlarmStateUseCase: GetAlarmStateUseCase,
    private val updateAlarmStateUseCase: UpdateAlarmStateUseCase,
    getAuthStateUseCase: GetAuthStateUseCase,
    getNetworkStateUseCase: GetNetworkStateUseCase
) : ViewModel() {
    val isOnline: StateFlow<Boolean> = getNetworkStateUseCase().map {
        it
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    val loginWithOutAuth = getAuthStateUseCase().map {
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


    fun getComposeAuth() = getComposeAuthUseCase()


    fun checkLoginState(result: NativeSignInResult) {
        when (result) {
            is NativeSignInResult.Success -> {
                viewModelScope.launch {
                    loginSucceededUseCase()
                }
            }

            else -> {}
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase()
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