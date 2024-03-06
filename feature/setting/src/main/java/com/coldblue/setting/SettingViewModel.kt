package com.coldblue.setting

import androidx.lifecycle.ViewModel
import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.model.AlarmItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val scheduler: AlarmScheduler
) : ViewModel() {

    fun schedule(item: AlarmItem) {
        scheduler.schedule(item)
    }

    fun cancel(item: AlarmItem) {
        scheduler.cancel(item)
    }


}