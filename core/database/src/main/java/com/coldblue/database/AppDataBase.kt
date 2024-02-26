package com.coldblue.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coldblue.database.convert.LocalDateConvert
import com.coldblue.database.dao.CurrentGroupDao
import com.coldblue.database.dao.MandaDao
import com.coldblue.database.dao.MandaDetailDao
import com.coldblue.database.dao.TodoDao
import com.coldblue.database.dao.TodoGroupDao
import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.MandaDetailEntity
import com.coldblue.database.entity.MandaEntity
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoGroupEntity

@Database(
    entities = [
        CurrentGroupEntity::class,
        MandaEntity::class,
        MandaDetailEntity::class,
        TodoEntity::class,
        TodoGroupEntity::class,
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(LocalDateConvert::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun currentGroupDao(): CurrentGroupDao
    abstract fun mandaDao(): MandaDao
    abstract fun mandaDetailDao(): MandaDetailDao
    abstract fun todoDao(): TodoDao
    abstract fun todoGroupDao(): TodoGroupDao

}