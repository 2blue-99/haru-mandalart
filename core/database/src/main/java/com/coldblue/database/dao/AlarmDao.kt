package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.AlarmEntity

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm")
    suspend fun getAllAlarmId(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlarmId(alarm: AlarmEntity)

    @Delete
    suspend fun deleteAlarmId(alarm: AlarmEntity)
}