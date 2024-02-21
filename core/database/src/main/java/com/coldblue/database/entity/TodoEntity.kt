package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "origin_id")
    val originId: Int,
    @ColumnInfo(name = "harumanda_id")
    val harumandaId: Int,
    @ColumnInfo(name = "is_sync")
    val isSync: Boolean,
    @ColumnInfo(name = "is_del")
    val isDel: Boolean,
    @ColumnInfo(name = "update_time")
    val updateTime: String,
    val title: String,
    val content: String,
    val time: String
)
