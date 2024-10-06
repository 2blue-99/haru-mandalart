package com.coldblue.data.di

import android.content.Context
import com.coldblue.data.receiver.notification.NotificationScheduler
import com.coldblue.data.receiver.notification.NotificationSchedulerImpl
import com.coldblue.database.dao.NotificationDao
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
    fun provideNotificationScheduler(
        @ApplicationContext context: Context,
        notificationDao: NotificationDao
    ): NotificationScheduler = NotificationSchedulerImpl(context, notificationDao)
}