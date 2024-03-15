package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldblue.database.entity.TodoGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoGroupDao {
    @Query("Select * From todo_group WHERE is_del=0")
    fun getTodoGroup(): Flow<List<TodoGroupEntity>>

    @Query("UPDATE todo_group SET name = :name WHERE id = :todoGroupId")
    suspend fun upsertTodoGroup(todoGroupId: Int, name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodoGroup(todoGroup: TodoGroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodoGroup(todoGroups: List<TodoGroupEntity>)

    @Transaction
    suspend fun deleteTodoGroupAndRelated(todoGroupId: Int) {
        deleteTodoGroup(todoGroupId)
        deleteCurrentGroup(todoGroupId)
        setOffTodo(todoGroupId)
    }

    @Query("UPDATE todo_group SET is_del = 1 WHERE id = :todoGroupId")
    suspend fun deleteTodoGroup(todoGroupId: Int)

    @Query("UPDATE current_group SET is_del = 1 WHERE todo_group_id = :todoGroupId")
    suspend fun deleteCurrentGroup(todoGroupId: Int)

    @Query("UPDATE todo SET todo_group_id = NULL WHERE todo_group_id = :todoGroupId")
    suspend fun setOffTodo(todoGroupId: Int)


    @Query("SELECT * FROM todo_group WHERE update_time > :updateTime AND is_sync=0")
    fun getToWriteTodoGroups(updateTime: String): List<TodoGroupEntity>

    @Transaction
    fun getTodoGroupIdByOriginIds(originIds: List<Int>): List<Int?> {
        return originIds.map { originId ->
            getTodoGroupIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM todo_group WHERE origin_id = :originId")
    fun getTodoGroupIdByOriginId(originId: Int): Int?
}