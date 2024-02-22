package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_group_haru_manda_relation")
data class TodoGroupHaruMandaRelationEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "origin_id")
    val originId: Int,
    @ColumnInfo(name = "todo_group_id")
    val todoGroupId: Int,
    @ColumnInfo(name = "haru_manda_id")
    val haruMandaId: Int,
    @ColumnInfo(name = "is_sync")
    val isSync: Boolean,
    @ColumnInfo(name = "is_del")
    val isDel: Boolean,
    @ColumnInfo(name = "update_time")
    val updateTime: Boolean
)
