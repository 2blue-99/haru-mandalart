package com.coldblue.data.di

import android.app.AlarmManager
import android.content.Context
import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.alarm.AlarmSchedulerImpl
import com.coldblue.database.dao.AlarmDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {
    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager =
        context.getSystemService(AlarmManager::class.java)

    @Singleton
    @Provides
    fun provideAlarmScheduler(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager,
        alarmDao: AlarmDao
    ): AlarmScheduler = AlarmSchedulerImpl(context, alarmManager, alarmDao)


}