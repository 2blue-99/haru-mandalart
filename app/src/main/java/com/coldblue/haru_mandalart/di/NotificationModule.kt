package com.coldblue.haru_mandalart.di

import android.content.Context
import com.coldblue.data.alarm.TodoNotificationService
import com.coldblue.haru_mandalart.notification.TodoNotificationServiceImpl
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
        TodoNotificationServiceImpl(context)
}