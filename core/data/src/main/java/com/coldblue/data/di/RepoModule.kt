package com.coldblue.data.di

import com.coldblue.data.repo.MandaDetailRepository
import com.coldblue.data.repo.MandaDetailRepositoryImpl
import com.coldblue.data.repo.MandaKeyRepository
import com.coldblue.data.repo.MandaKeyRepositoryImpl
import com.coldblue.data.repo.TodoRepository
import com.coldblue.data.repo.TodoRepositoryImpl
import com.coldblue.data.repo.UserRepository
import com.coldblue.data.repo.UserRepositoryImpl
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
    fun bindMandaKeyRepo(
        mandaRepo: MandaKeyRepositoryImpl
    ): MandaKeyRepository

    @Binds
    fun bindMandaDetailRepo(
        mandaRepo: MandaDetailRepositoryImpl
    ): MandaDetailRepository

    @Binds
    fun bindTodoRepo(
        todoRepo: TodoRepositoryImpl
    ): TodoRepository

    @Binds
    fun bindUserPreferencesRepo(
        userPreferencesRepo: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindLoginHelperRepo(
        loginHelper: LoginHelperImpl
    ): LoginHelper

}