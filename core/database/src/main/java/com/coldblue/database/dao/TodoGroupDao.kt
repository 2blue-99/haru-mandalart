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
    fun getTodoGroupEntityEntities():  Flow<List<TodoGroupEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodoGroupEntityEntities(todoGroupEntities: List<TodoGroupEntity>)
    @Query("Delete From todo_group")
    suspend fun deleteTodoGroupEntityEntities()
}