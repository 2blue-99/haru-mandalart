package com.coldblue.database.di

import com.coldblue.database.AppDataBase
import com.coldblue.database.dao.AlarmDao
import com.coldblue.database.dao.CurrentGroupDao
import com.coldblue.database.dao.AppDao
import com.coldblue.database.dao.MandaDetailDao
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.database.dao.TodoDao
import com.coldblue.database.dao.TodoGroupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun provideAlarmDao(dataBase: AppDataBase): AlarmDao =
        dataBase.alarmDao()

    @Singleton
    @Provides
    fun provideCurrentGroupDao(dataBase: AppDataBase): CurrentGroupDao =
        dataBase.currentGroupDao()

    @Singleton
    @Provides
    fun provideMandaKeyDao(dataBase: AppDataBase): MandaKeyDao =
        dataBase.mandaKeyDao()

    @Singleton
    @Provides
    fun provideMandaDetailDao(dataBase: AppDataBase): MandaDetailDao =
        dataBase.mandaDetailDao()

    @Singleton
    @Provides
    fun provideTodoDao(dataBase: AppDataBase): TodoDao =
        dataBase.todoDao()

    @Singleton
    @Provides
    fun provideTodoGroupDao(dataBase: AppDataBase): TodoGroupDao =
        dataBase.todoGroupDao()

    @Singleton
    @Provides
    fun provideHaruMandalartDao(dataBase: AppDataBase): AppDao =
        dataBase.haruMandalrtDao()


}