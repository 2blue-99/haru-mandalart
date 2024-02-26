package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.TodoGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoGroupDao {
    @Query("Select * From todo_group")
    fun getTodoGroup(): Flow<List<TodoGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodoGroup(todoGroupEntities: TodoGroupEntity)

    @Query("Delete From todo_group WHERE id = :todoGroupId")
    suspend fun deleteTodoGroup(todoGroupId: Int)
}