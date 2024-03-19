package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "todo")
data class TodoEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "time") val time: LocalTime?,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "origin_group_id") val originGroupId: Int,
    @ColumnInfo(name = "todo_group_id") val todoGroupId: Int? = null,
    @ColumnInfo(name = "origin_id") override val originId: Int,
    @ColumnInfo(name = "is_sync") override val isSync: Boolean,
    @ColumnInfo(name = "is_del") val isDel: Boolean,
    @ColumnInfo(name = "update_time") override val updateTime: String,
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    ) : SyncableEntity

data class TodoWithGroupName(
    @Embedded val todo: TodoEntity,
    @ColumnInfo(name = "groupName") val groupName: String?
)
