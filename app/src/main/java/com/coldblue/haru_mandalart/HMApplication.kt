package com.coldblue.haru_mandalart

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.coldblue.data.notification.TodoNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            TodoNotificationService.CHANNEL_ID,
            TodoNotificationService.CHANNEL_NAME,
            TodoNotificationService.IMPORTANCE,
        )
        channel.description = TodoNotificationService.description

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}