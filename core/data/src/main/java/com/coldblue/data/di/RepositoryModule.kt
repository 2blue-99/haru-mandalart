package com.coldblue.data.di

import com.coldblue.data.repository.MandaDetailRepository
import com.coldblue.data.repository.MandaDetailRepositoryImpl
import com.coldblue.data.repository.MandaKeyRepository
import com.coldblue.data.repository.MandaKeyRepositoryImpl
import com.coldblue.data.repository.CurrentGroupRepository
import com.coldblue.data.repository.CurrentGroupRepositoryImpl
import com.coldblue.data.repository.TodoGroupRepository
import com.coldblue.data.repository.TodoGroupRepositoryImpl
import com.coldblue.data.repository.TodoRepository
import com.coldblue.data.repository.TodoRepositoryImpl
import com.coldblue.data.repository.UserRepository
import com.coldblue.data.repository.UserRepositoryImpl
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindMandaKeyRepo(
        mandaRepo: MandaKeyRepositoryImpl
    ): MandaKeyRepository

    @Binds
    fun bindMandaDetailRepo(
        mandaRepo: MandaDetailRepositoryImpl
    ): MandaDetailRepository

    @Binds
    fun bindTodoRepository(
        todoRepository: TodoRepositoryImpl
    ): TodoRepository

    @Binds
    fun bindCurrentGroupRepository(
        currentGroupRepository: CurrentGroupRepositoryImpl
    ): CurrentGroupRepository
    @Binds
    fun bindTodoGroupRepository(
        todoGroupRepository: TodoGroupRepositoryImpl
    ): TodoGroupRepository

    @Binds
    fun bindUserPreferencesRepo(
        userPreferencesRepo: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindLoginHelperRepo(
        loginHelper: LoginHelperImpl
    ): LoginHelper

}