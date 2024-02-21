package com.coldblue.data.di

import com.coldblue.data.repo.MandaRepo
import com.coldblue.data.repo.MandaRepoImpl
import com.coldblue.data.repo.TodoRepo
import com.coldblue.data.repo.TodoRepoImpl
import com.coldblue.data.repo.UserPreferencesRepo
import com.coldblue.data.repo.UserPreferencesRepoImpl
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginHelperImpl
import com.coldblue.datastore.UserPreferencesDataStore
import com.coldblue.datastore.UserPreferencesDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {
    @Binds
    fun bindUserPreferencesRepo(
        userPreferencesDataStore: UserPreferencesDataStoreImpl
    ): UserPreferencesDataStore
    @Binds
    fun bindMandaRepo(
        mandaRepo: MandaRepoImpl
    ): MandaRepo
    @Binds
    fun bindTodoRepo(
        todoRepo: TodoRepoImpl
    ): TodoRepo
    @Binds
    fun bindLoginHelper(
        loginHelper: LoginHelperImpl
    ): LoginHelperImpl
}