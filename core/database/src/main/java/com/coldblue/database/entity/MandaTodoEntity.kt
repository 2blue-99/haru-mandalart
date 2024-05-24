package com.coldblue.database.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "manda_todo")
data class MandaTodoEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "manda_index") val mandaIndex: Int,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "is_alarm") val isAlarm: Boolean,
    @ColumnInfo(name = "time") val time: LocalTime?,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "origin_id") override val originId: Int,
    @ColumnInfo(name = "is_sync") override val isSync: Boolean,
    @ColumnInfo(name = "is_del") val isDel: Boolean,
    @ColumnInfo(name = "update_time") override val updateTime: String,
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
) : SyncableEntity