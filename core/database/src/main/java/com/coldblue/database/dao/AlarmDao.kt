package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm")
    suspend fun getAllAlarmId(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlarmId(id: Int)

    @Delete
    suspend fun deleteAlarmId(id: Int)
}