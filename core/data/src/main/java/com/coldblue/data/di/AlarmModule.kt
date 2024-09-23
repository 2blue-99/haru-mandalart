package com.coldblue.data.di

import android.content.Context
import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.alarm.AlarmSchedulerImpl
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
    fun provideAlarmHelper(@ApplicationContext context: Context): AlarmScheduler = AlarmSchedulerImpl(context)
}