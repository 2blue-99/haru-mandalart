package com.coldblue.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.SettingHelper
import com.coldblue.domain.user.GetEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingHelper: SettingHelper,
    private val loginHelper: LoginHelper,
    private val getEmailUseCase: GetEmailUseCase
) : ViewModel() {
    val versionName = settingHelper.versionName
    val email = getEmailUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
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

}