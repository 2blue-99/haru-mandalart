package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manda_detail")
data class MandaDetailEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "is_sync")
    val isSync: Boolean,
    @ColumnInfo(name = "is_del")
    val isDel: Boolean,
    @ColumnInfo(name = "update_time")
    val updateTime: String,
    @ColumnInfo(name = "manda_id")
    val mandaId: Int,
    val name: String,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean,
)
