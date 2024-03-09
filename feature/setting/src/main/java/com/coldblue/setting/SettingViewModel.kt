package com.coldblue.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.util.SettingHelper
import com.coldblue.model.AlarmItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingHelper: SettingHelper
) : ViewModel() {

    val versionName = settingHelper.versionName

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