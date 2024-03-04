package com.coldblue.database.convert

import androidx.room.TypeConverter
import java.time.LocalTime

class LocalTimeConverter {
    @TypeConverter
    fun localTimeToLong(localTime: LocalTime?): Long? {
        return localTime?.toNanoOfDay()
    }

    @TypeConverter
    fun longToLocalTime(nanoOfDay: Long?): LocalTime? {
        return nanoOfDay?.let { LocalTime.ofNanoOfDay(it) }
    }
}