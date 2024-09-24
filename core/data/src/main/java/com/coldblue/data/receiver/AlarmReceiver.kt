package com.coldblue.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.coldblue.data.receiver.alarm.AlarmAppInterface
import com.coldblue.data.repository.user.UserRepository
import com.coldblue.database.dao.NotificationDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var alarmAppInterface: AlarmAppInterface

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var notificationDao: NotificationDao

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("TAG", "onReceive: 받았음")

        val title = intent?.getStringExtra(ALARM_TITLE) ?: ""
        val id = intent?.getIntExtra(ALARM_ID, 0) ?: 0

        CoroutineScope(Dispatchers.IO).launch {
            notificationDao.deleteNotification(id)
            if (userRepository.isAlarm.first())
                showAlarm(title)
        }
    }

    private fun showAlarm(text: String){
        alarmAppInterface.showAlarm(text)
    }
}

internal const val ALARM_TITLE = "ALARM_TITLE"
internal const val ALARM_ID = "ALARM_ID"