package com.coldblue.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.coldblue.data.mapper.AlarmMapper.asDomain
import com.coldblue.database.dao.AlarmDao
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {
    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var alarmDao: AlarmDao

    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.d("BootCompletedReceiver : $intent")
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            addAllAlarm()
        }
    }

    private fun addAllAlarm(){
        CoroutineScope(Dispatchers.IO).launch {
            alarmDao.getAllAlarm().forEach {
                alarmScheduler.add(it.asDomain())
            }
        }
    }

}