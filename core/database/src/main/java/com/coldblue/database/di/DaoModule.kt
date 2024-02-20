package com.coldblue.database.di

import android.content.Context
import androidx.room.Room
import com.coldblue.database.AppDataBase
import com.coldblue.database.dao.MandaDao
import com.coldblue.database.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun provideTodoDao(dataBase: AppDataBase): TodoDao =
        dataBase.todoDao()

    @Singleton
    @Provides
    fun provideMandaDao(dataBase: AppDataBase): MandaDao =
        dataBase.mandaDao()


}