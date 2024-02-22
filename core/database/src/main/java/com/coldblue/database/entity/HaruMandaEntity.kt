package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "haru_manda")
data class HaruMandaEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "origin_id")
    val originId: Int,
    @ColumnInfo(name = "is_sync")
    val isSync: Boolean,
    @ColumnInfo(name = "is_del")
    val isDel: Boolean,
    @ColumnInfo(name = "update_time")
    val updateTime: String,
    val date: LocalDate,
)
