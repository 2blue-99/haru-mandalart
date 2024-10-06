package com.coldblue.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.coldblue.data.receiver.notification.NotificationAppService
import com.coldblue.data.repository.user.UserRepository
import com.coldblue.database.dao.NotificationDao
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var notificationAppService: NotificationAppService

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var notificationDao: NotificationDao

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra(NOTICE_TITLE) ?: ""
        val id = intent?.getIntExtra(NOTICE_ID, 0) ?: 0
        Log.e("TAG", "Alarm onReceive :$title / $id")

        CoroutineScope(Dispatchers.IO).launch {
            // 알람 실행 동의 여부 확인
            if (userRepository.isAlarm.first()){
                // 앱 실행 or 미실행 확인
                Logger.d("notificationAppService.isAppForeground() :${notificationAppService.isAppForeground()}")
                if(notificationAppService.isAppForeground()) {
                    showNotification(title)
                }else{
                    showAlarm(title)
                }
            }
            notificationDao.deleteNotification(id)
        }
    }

    private fun showNotification(text: String) {
        notificationAppService.showNotification(text)
    }

    private fun showAlarm(text: String){
        notificationAppService.showAlarm(text)
    }
}

const val NOTICE_TITLE = "ALARM_TITLE"
const val NOTICE_ID = "ALARM_ID"