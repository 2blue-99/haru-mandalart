package com.coldblue.haru_mandalart

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.coldblue.haru_mandalart.notification.TodoNotificationServiceImpl
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initLogger()
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

    private fun initLogger() {
        val strategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .tag("logger").showThreadInfo(false).methodCount(1)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(strategy))
    }
}