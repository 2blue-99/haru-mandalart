package com.coldblue.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmEntity(
    @PrimaryKey val id: Int
)
