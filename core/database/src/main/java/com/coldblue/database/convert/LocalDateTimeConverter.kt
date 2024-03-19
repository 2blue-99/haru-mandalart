package com.coldblue.database.convert

import androidx.room.TypeConverter
import com.orhanobut.logger.Logger
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime): String {
        return localDateTime.format(formatter)
    }

    @TypeConverter
    fun stringToLocalDateTime(date: String?): LocalDateTime? {
        return if (date.isNullOrBlank()) null
        else LocalDateTime.parse(date, formatter)
    }
}