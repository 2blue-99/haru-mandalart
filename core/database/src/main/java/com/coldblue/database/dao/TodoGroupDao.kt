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
    @Query("Select * From todo_group")
    fun getTodoGroup(): Flow<List<TodoGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodoGroup(todoGroup: TodoGroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodoGroup(todoGroups: List<TodoGroupEntity>)

    @Query("Delete From todo_group WHERE id = :todoGroupId")
    suspend fun deleteTodoGroup(todoGroupId: Int)


    @Transaction
    fun getTodoGroupIdByOriginIds(originIds: List<Int>): List<Int?> {
        return originIds.map { originId ->
            getTodoGroupIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM todo_group WHERE origin_id = :originId")
    fun getTodoGroupIdByOriginId(originId: Int): Int?
}