package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "alarm")
data class AlarmEntity(
    @ColumnInfo(name = "time") val time: LocalDateTime?,
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey val id: Int
)
