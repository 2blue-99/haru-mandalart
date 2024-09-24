package com.coldblue.haru_mandalart.di

import android.content.Context
import com.coldblue.data.receiver.alarm.AlarmAppInterface
import com.coldblue.haru_mandalart.alarm.AlarmAppInterfaceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmAppModule {
    @Singleton
    @Provides
    fun provideAlarmHelper(@ApplicationContext context: Context): AlarmAppInterface = AlarmAppInterfaceImpl(context)
}