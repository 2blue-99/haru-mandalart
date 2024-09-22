package com.coldblue.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.coldblue.data.mapper.AlarmMapper.asDomain
import com.coldblue.database.dao.AlarmDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationScheduler: NotificationScheduler

    @Inject
    lateinit var alarmDao: AlarmDao

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            addAllAlarm()
        }
    }

    private fun addAllAlarm(){
        CoroutineScope(Dispatchers.IO).launch {
            alarmDao.getAllAlarm().forEach {
                notificationScheduler.add(it.asDomain())
            }
        }
    }
}
