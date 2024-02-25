package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("Select * From todo")
    fun getTodoEntities(): Flow<List<TodoEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodoEntities(todoEntities: List<TodoEntity>)
    @Query("Delete From todo")
    suspend fun deleteTodoEntities()
}