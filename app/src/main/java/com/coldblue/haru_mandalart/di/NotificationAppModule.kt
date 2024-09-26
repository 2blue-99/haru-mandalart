package com.coldblue.haru_mandalart.di

import android.content.Context
import com.coldblue.data.receiver.notification.NotificationAppService
import com.coldblue.haru_mandalart.notification.NotificationAppServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationAppModule {
    @Singleton
    @Provides
    fun provideNotificationAppService(@ApplicationContext context: Context): NotificationAppService = NotificationAppServiceImpl(context)
}