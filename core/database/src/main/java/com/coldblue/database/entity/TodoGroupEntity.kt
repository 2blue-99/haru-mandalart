package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_group")
data class TodoGroupEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "origin_id") override val originId: Int,
    @ColumnInfo(name = "is_sync") override val isSync: Boolean,
    @ColumnInfo(name = "is_del") val isDel: Boolean,
    @ColumnInfo(name = "update_time") override val updateTime: String,
    @PrimaryKey(autoGenerate = true) override val id: Int = 0
) : SyncableEntity
