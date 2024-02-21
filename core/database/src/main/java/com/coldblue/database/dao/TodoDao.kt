package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.MandaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodoEntities(mandaEntity: List<MandaEntity>)
    @Query("Select * From manda")
    fun getTodoEntities(): Flow<List<MandaEntity>>
    @Query("Delete From manda")
    fun deleteTodoEntities()
}