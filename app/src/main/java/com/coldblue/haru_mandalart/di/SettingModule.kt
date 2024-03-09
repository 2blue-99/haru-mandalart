package com.coldblue.haru_mandalart.di

import android.content.Context
import com.coldblue.data.util.SettingHelper
import com.coldblue.haru_mandalart.util.SettingHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingModule {
    @Singleton
    @Provides
    fun provideSettingHelper(@ApplicationContext context: Context): SettingHelper = SettingHelperImpl(context)

}