package com.coldblue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "current_group")
data class CurrentGroupEntity(
    @ColumnInfo(name = "todo_group_id") val todoGroupId: Int,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "is_sync") override val isSync: Boolean,
    @ColumnInfo(name = "is_del") val isDel: Boolean,
    @ColumnInfo(name = "update_time") override val updateTime: String,
    @ColumnInfo(name = "origin_id") override val originId: Int,
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
):SyncableEntity

data class CurrentGroupWithName(
    @Embedded val currentGroup: CurrentGroupEntity,
    @ColumnInfo(name = "groupName") val groupName: String
)

