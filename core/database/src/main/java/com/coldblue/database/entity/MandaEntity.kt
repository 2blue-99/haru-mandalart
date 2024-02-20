package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manda")
data class MandaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "is_Sync")
    val isSync: Boolean,
    @ColumnInfo(name = "is_del")
    val isDel: Boolean,
    @ColumnInfo(name = "update_time")
    val updateTime: String,
    val name: String,
    val color: Long
)