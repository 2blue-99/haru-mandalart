package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AppDao {

    @Transaction
    suspend fun reset() {
        deleteAllTodo()
        deleteAllTodoGroup()
        deleteAllCurrentGroup()
        deleteAllTMandaKey()
        deleteAllTMandaDetail()
        deleteAllAlarm()
    }

    @Query("DELETE FROM manda_todo")
    suspend fun deleteAllTodo()

    @Query("DELETE FROM ALARM")
    suspend fun deleteAllAlarm()

    @Query("DELETE FROM todo_group")
    suspend fun deleteAllTodoGroup()

    @Query("DELETE FROM current_group")
    suspend fun deleteAllCurrentGroup()

    @Query("DELETE FROM manda_key")
    suspend fun deleteAllTMandaKey()

    @Query("DELETE FROM manda_detail")
    suspend fun deleteAllTMandaDetail()

}