package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.NotificationEntity

@Dao
interface NotificationDao {
    @Query("SELECT * FROM alarm")
    suspend fun getAllNotification(): List<NotificationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotification(notification: NotificationEntity)

    @Query("DELETE FROM alarm WHERE id = :id")
    suspend fun deleteNotification(id: Int)
}