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
    suspend fun upsertTodoEntities(todoEntities: List<TodoDao>)
    @Query("Select * From todo")
    fun getTodoEntities(): Flow<List<TodoDao>>
    @Query("Delete From todo")
    fun deleteTodoEntities()
}