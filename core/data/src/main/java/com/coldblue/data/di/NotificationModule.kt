package com.coldblue.data.di

import android.app.AlarmManager
import android.content.Context
import com.coldblue.data.notification.NotificationScheduler
import com.coldblue.data.notification.NotificationSchedulerImpl
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
    fun provideNotificationManager(@ApplicationContext context: Context): AlarmManager =
        context.getSystemService(AlarmManager::class.java)

    @Singleton
    @Provides
    fun provideNotificationScheduler(
        @ApplicationContext context: Context,
        notificationManager: AlarmManager,
        notificationDao: NotificationDao
    ): NotificationScheduler = NotificationSchedulerImpl(context, notificationManager, notificationDao)


}