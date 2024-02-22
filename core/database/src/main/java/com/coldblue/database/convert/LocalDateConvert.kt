package com.coldblue.database.convert

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConvert {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @TypeConverter
    fun localDateToString(localDate: LocalDate): String =
        localDate.format(formatter)
    @TypeConverter
    fun stringToLocalDate(date: String): LocalDate =
        LocalDate.parse(date, formatter)
}