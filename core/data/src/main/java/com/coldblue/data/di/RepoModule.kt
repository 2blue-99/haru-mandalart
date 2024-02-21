package com.coldblue.data.di

import com.coldblue.data.repo.MandaRepo
import com.coldblue.data.repo.MandaRepoImpl
import com.coldblue.data.repo.TodoRepo
import com.coldblue.data.repo.TodoRepoImpl
import com.coldblue.data.repo.UserPreferencesRepo
import com.coldblue.data.repo.UserPreferencesRepoImpl
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginHelperImpl
import com.coldblue.datastore.UserPreferencesDataSource
import com.coldblue.datastore.UserPreferencesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {
    @Binds
    fun bindMandaRepo(
        mandaRepo: MandaRepoImpl
    ): MandaRepo
    @Binds
    fun bindTodoRepo(
        todoRepo: TodoRepoImpl
    ): TodoRepo
    @Binds
    fun bindUserPreferencesRepo(
        userPreferencesRepo: UserPreferencesRepoImpl
    ): UserPreferencesRepo
    @Binds
    fun bindLoginHelperRepo(
        loginHelper: LoginHelperImpl
    ): LoginHelper

}