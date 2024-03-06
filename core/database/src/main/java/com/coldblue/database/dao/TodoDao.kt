package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoWithGroupName
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TodoDao {
    @Query("SELECT todo.*, IFNULL(todo_group.name, '') AS groupName FROM todo LEFT JOIN todo_group ON todo.todo_group_id = todo_group.id WHERE date=:date AND todo.is_del=0")
    fun getTodo(date: LocalDate): Flow<List<TodoWithGroupName>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodo(todo: TodoEntity)

    @Query("Delete From todo WHERE id = :todoId")
    suspend fun deleteTodo(todoId: Int)

}