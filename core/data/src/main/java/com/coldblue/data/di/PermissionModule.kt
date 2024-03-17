package com.coldblue.data.di

import android.content.Context
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.sync.SyncHelperImpl
import com.coldblue.data.util.PermissionHelper
import com.coldblue.data.util.PermissionHelperImpl
import com.coldblue.datastore.UserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PermissionModule {
    @Provides
    @Singleton
    fun providePermissionHelper(userDataSource: UserDataSource): PermissionHelper {
        return PermissionHelperImpl(userDataSource)
    }
}
