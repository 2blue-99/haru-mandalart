package com.coldblue.data.di

import android.app.AlarmManager
import android.content.Context
import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.alarm.AlarmSchedulerImpl
import com.coldblue.data.notification.TodoNotificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Singleton
    @Provides
    fun provideTodoNotificationService(@ApplicationContext context: Context): TodoNotificationService =
        TodoNotificationService(context)

}