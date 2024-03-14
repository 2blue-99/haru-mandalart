package com.coldblue.datastore.di

import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.datastore.UpdateTimeDataSourceImpl
import com.coldblue.datastore.UserDataSource
import com.coldblue.datastore.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun bindUserPreferencesRepo(
        userPreferencesDataStore: UserDataSourceImpl
    ): UserDataSource

    @Binds
    fun bindUpdateTimePreferencesRepo(
        updateTimePreferencesDataStore: UpdateTimeDataSourceImpl
    ): UpdateTimeDataSource
}

