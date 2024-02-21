package com.coldblue.datastore.di

import com.coldblue.datastore.UserPreferencesDataSource
import com.coldblue.datastore.UserPreferencesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun bindUserPreferencesRepo(
        userPreferencesDataStore: UserPreferencesDataSourceImpl
    ): UserPreferencesDataSource
}