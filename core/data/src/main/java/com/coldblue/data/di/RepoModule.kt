package com.coldblue.data.di

import com.coldblue.data.repo.MandaRepo
import com.coldblue.data.repo.MandaRepoImpl
import com.coldblue.data.repo.TodoRepo
import com.coldblue.data.repo.TodoRepoImpl
import com.coldblue.data.repo.UserRepo
import com.coldblue.data.repo.UserRepoImpl
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginHelperImpl
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
        userPreferencesRepo: UserRepoImpl
    ): UserRepo
    @Binds
    fun bindLoginHelperRepo(
        loginHelper: LoginHelperImpl
    ): LoginHelper

}