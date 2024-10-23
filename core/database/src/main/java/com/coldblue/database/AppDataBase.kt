package com.coldblue.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coldblue.database.convert.LocalDateConvert
import com.coldblue.database.convert.LocalDateTimeConverter
import com.coldblue.database.convert.LocalTimeConverter
import com.coldblue.database.dao.NotificationDao
import com.coldblue.database.dao.AppDao
import com.coldblue.database.dao.MandaDetailDao
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.database.dao.MandaTodoDao
import com.coldblue.database.entity.NotificationEntity
import com.coldblue.database.entity.MandaDetailEntity
import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.database.entity.MandaTodoEntity

@Database(
    entities = [
        NotificationEntity::class,
        MandaKeyEntity::class,
        MandaDetailEntity::class,
        MandaTodoEntity::class
    ],
    version = 3,
    exportSchema = true,
)

@TypeConverters(LocalDateConvert::class, LocalDateTimeConverter::class, LocalTimeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun alarmDao(): NotificationDao
    abstract fun mandaKeyDao(): MandaKeyDao
    abstract fun mandaDetailDao(): MandaDetailDao
    abstract fun haruMandalrtDao(): AppDao
    abstract fun mandaTodoDao(): MandaTodoDao

}
