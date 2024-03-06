package com.coldblue.haru_mandalart

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.coldblue.haru_mandalart.notification.TodoNotificationServiceImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            TodoNotificationServiceImpl.CHANNEL_ID,
            TodoNotificationServiceImpl.CHANNEL_NAME,
            TodoNotificationServiceImpl.IMPORTANCE,
        )
        channel.description = TodoNotificationServiceImpl.description

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}