package com.coldblue.data.di

import android.content.Context
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.sync.SyncHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SyncModule {
    @Provides
    @Singleton
    fun provideSyncWriteHelper(
        @ApplicationContext appContext: Context,
    ): SyncHelper {
        return SyncHelperImpl(appContext)
    }


}
