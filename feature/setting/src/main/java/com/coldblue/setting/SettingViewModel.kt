package com.coldblue.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.LoginState
import com.coldblue.domain.auth.DeleteUserUseCase
import com.coldblue.domain.auth.GetAuthStateUseCase
import com.coldblue.domain.auth.GetComposeAuthUseCase
import com.coldblue.domain.auth.LoginSucceededUseCase
import com.coldblue.domain.auth.LogoutUseCase
import com.coldblue.domain.network.GetNetworkStateUseCase
import com.coldblue.domain.setting.GetVersionUseCase
import com.coldblue.domain.setting.ShowContactUseCase
import com.coldblue.domain.setting.ShowOssUseCase
import com.coldblue.domain.setting.ShowPlayStoreUseCase
import com.coldblue.domain.user.GetAlarmStateUseCase
import com.coldblue.domain.user.GetEmailUseCase
import com.coldblue.domain.user.UpdateAlarmStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    getEmailUseCase: GetEmailUseCase,
    getAlarmStateUseCase: GetAlarmStateUseCase,
    private val updateAlarmStateUseCase: UpdateAlarmStateUseCase,
    getAuthStateUseCase: GetAuthStateUseCase,
    getNetworkStateUseCase: GetNetworkStateUseCase,
    getVersionUseCase: GetVersionUseCase,
    private val showContactUseCase: ShowContactUseCase,
    private val showOssUseCase: ShowOssUseCase,
    private val showPlayStoreUseCase: ShowPlayStoreUseCase,

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
        initialValue = com.coldblue.data.util.LoginState.Loading
    )
    val versionName = getVersionUseCase()

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
        showOssUseCase()
    }

    fun showContact() {
        showContactUseCase()
    }

    fun showPlayStore() {
        showPlayStoreUseCase()
    }

    fun updateAlarmState(state: Boolean) {
        viewModelScope.launch {
            updateAlarmStateUseCase(state)
        }
    }
}