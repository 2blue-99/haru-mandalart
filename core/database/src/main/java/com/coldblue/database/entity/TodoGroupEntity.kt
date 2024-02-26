package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_group")
data class TodoGroupEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "origin_id") val originId: Int,
    @ColumnInfo(name = "is_sync") val isSync: Boolean,
    @ColumnInfo(name = "is_del") val isDel: Boolean,
    @ColumnInfo(name = "update_time") val updateTime: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
