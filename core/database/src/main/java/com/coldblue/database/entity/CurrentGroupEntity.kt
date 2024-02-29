package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "current_group")
data class CurrentGroupEntity(
    @ColumnInfo(name = "todo_group_id") val todoGroupId: Int,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "is_sync") val isSync: Boolean,
    @ColumnInfo(name = "is_del") val isDel: Boolean,
    @ColumnInfo(name = "update_time") val updateTime: String,
    @ColumnInfo(name = "origin_id") val originId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
