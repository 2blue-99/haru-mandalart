package com.coldblue.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.SettingHelper
import com.coldblue.domain.user.GetAlarmStateUseCase
import com.coldblue.domain.user.GetEmailUseCase
import com.coldblue.domain.user.UpdateAlarmStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingHelper: SettingHelper,
    private val loginHelper: LoginHelper,
    private val getEmailUseCase: GetEmailUseCase,
    private val getAlarmStateUseCase: GetAlarmStateUseCase,
    private val updateAlarmStateUseCase: UpdateAlarmStateUseCase
) : ViewModel() {
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


    fun logout() {
        viewModelScope.launch {
            loginHelper.setLogout()
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

    fun updateAlarmState(state: Boolean){
        viewModelScope.launch{
            updateAlarmStateUseCase(state)
        }
    }

}