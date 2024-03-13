package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoWithGroupName
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    suspend fun getAllTodo(): List<TodoEntity>

    @Query("SELECT todo.*, IFNULL(todo_group.name, '') AS groupName FROM todo LEFT JOIN todo_group ON todo.todo_group_id = todo_group.id WHERE date=:date AND todo.is_del=0")
    fun getTodo(date: LocalDate): Flow<List<TodoWithGroupName>>

    @Query("SELECT DISTINCT(todo.date) FROM todo WHERE date >= :startDate AND date <= :endDate AND is_del=0")
    fun getYearlyExistTodoDate(startDate: LocalDate, endDate: LocalDate): Flow<List<LocalDate>>

    @Query("SELECT min(todo.date)  FROM todo WHERE is_del=0")
    fun getTodoMinYear(): Flow<LocalDate?>

    @Query("SELECT max(todo.date)  FROM todo WHERE is_del=0")
    fun getTodoMaxYear(): Flow<LocalDate?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodo(todo: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodos(todos: List<TodoEntity>)

    @Query("Delete From todo WHERE id = :todoId")
    suspend fun deleteTodo(todoId: Int)





    @Query("SELECT * FROM todo WHERE update_time > :updateTime AND is_sync=0")
    fun getToWriteTodos(updateTime: String): List<TodoEntity>
    @Transaction
    fun getTodoIdByOriginIds(originIds: List<Int>): List<Int?> {
        return originIds.map { originId ->
            getTodoIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM todo WHERE origin_id = :originId")
    fun getTodoIdByOriginId(originId: Int): Int?

}