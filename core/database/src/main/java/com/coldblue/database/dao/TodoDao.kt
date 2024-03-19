package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.CurrentGroupWithName
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoGroupEntity
import com.coldblue.database.entity.TodoWithGroupName
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TodoDao {

    @Query("SELECT todo.*, todo_group.name AS groupName FROM todo LEFT JOIN todo_group ON CASE WHEN todo.origin_group_id != 0 THEN todo.origin_group_id = todo_group.origin_id ELSE todo.todo_group_id = todo_group.id END WHERE todo.date=:date AND todo.is_del=0")
    fun getTodo(date: LocalDate): Flow<List<TodoWithGroupName>>

    @Query("SELECT todo.*, todo_group.name AS groupName FROM todo LEFT JOIN todo_group ON CASE WHEN todo.origin_group_id != 0 THEN todo.origin_group_id = todo_group.origin_id ELSE todo.todo_group_id = todo_group.id END WHERE todo.id=:todoId AND todo.is_del=0")
    fun getTodo(todoId: Int): Flow<TodoWithGroupName>

    @Query("SELECT DISTINCT(todo.date) FROM todo WHERE date >= :startDate AND date <= :endDate AND is_del=0")
    fun getYearlyExistTodoDate(startDate: LocalDate, endDate: LocalDate): Flow<List<LocalDate>>


    @Query("SELECT DISTINCT strftime('%Y',date) FROM todo WHERE is_del=0")
    fun getUniqueTodoYear(): Flow<List<String>?>

    @Query("SELECT COUNT(DISTINCT strftime('%Y-%M-%d',date)) FROM todo WHERE is_del=0")
    fun getUniqueTodoCountByDate(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodo(todo: TodoEntity)

    @Query("SELECT todo_group.origin_id FROM todo_group WHERE todo_group.id=:todoGroupId")
    fun getOriginGroupId(todoGroupId: Int?): Int?

    @Transaction
    suspend fun upsertTodo2(todo: TodoEntity) {
        val dd = getOriginGroupId(todo.todoGroupId)
        val originGroupId = if (todo.originGroupId == 0) getOriginGroupId(todo.todoGroupId)?:0 else todo.originGroupId
        upsertTodo(todo.copy(originGroupId = originGroupId))
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodo(todos: List<TodoEntity>)

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