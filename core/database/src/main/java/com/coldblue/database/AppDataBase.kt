package com.coldblue.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coldblue.database.dao.MandaDao
import com.coldblue.database.dao.TodoDao
import com.coldblue.database.entity.MandaEntity
import com.coldblue.database.entity.TodoEntity
@Database(
    entities = [
        TodoEntity::class,
        MandaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun mandaDao(): MandaDao
}