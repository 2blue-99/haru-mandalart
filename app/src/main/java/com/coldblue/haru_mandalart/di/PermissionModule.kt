package com.coldblue.haru_mandalart.di

import android.content.Context
import com.coldblue.data.util.PermissionHelper
import com.coldblue.haru_mandalart.permission.PermissionImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PermissionModule {
    @Singleton
    @Provides
    fun providePermission(
        permissionHelper: PermissionHelper
    ): PermissionImpl = PermissionImpl(permissionHelper)

}