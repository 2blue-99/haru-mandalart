package com.coldblue.database.di

import com.coldblue.database.AppDataBase
import com.coldblue.database.dao.AlarmDao
import com.coldblue.database.dao.AppDao
import com.coldblue.database.dao.MandaDetailDao
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.database.dao.MandaTodoDao
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
    fun provideMandaKeyDao(dataBase: AppDataBase): MandaKeyDao =
        dataBase.mandaKeyDao()

    @Singleton
    @Provides
    fun provideMandaDetailDao(dataBase: AppDataBase): MandaDetailDao =
        dataBase.mandaDetailDao()

    @Singleton
    @Provides
    fun provideMandaTodoDao(dataBase: AppDataBase): MandaTodoDao =
        dataBase.mandaTodoDao()

    @Singleton
    @Provides
    fun provideHaruMandalartDao(dataBase: AppDataBase): AppDao =
        dataBase.haruMandalrtDao()


}