package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoGroupEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface CurrentGroupDao {
    //    @Query("Select * From current_group")
//    fun getCurrentGroup(date:LocalDate): Flow<List<CurrentGroupEntity>>
    @Query("SELECT * FROM current_group WHERE date = :date OR date = (SELECT MAX(date) FROM current_group WHERE date < :date)")
    fun getCurrentGroup(date: LocalDate): Flow<List<CurrentGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrentGroup(currentGroups: List<CurrentGroupEntity>)

    @Transaction
    suspend fun deleteCurrentGroupWithTodo(currentGroupId: Int, todoGroupId: Int) {
        deleteCurrentGroup(currentGroupId)
        deleteTodoByCurrentGroup(todoGroupId)
    }

    @Query("Delete From current_group WHERE id = :currentGroupId")
    suspend fun deleteCurrentGroup(currentGroupId: Int)

    @Query("UPDATE todo SET todo_group_id=null WHERE todo_group_id = :todoGroupId")
    suspend fun deleteTodoByCurrentGroup(todoGroupId: Int)


    @Query("SELECT * FROM current_group WHERE update_time > :updateTime AND is_sync=0")
    fun getToWriteCurrentGroup(updateTime: String): List<CurrentGroupEntity>

    @Transaction
    fun getCurrentGroupIdByOriginIds(originIds: List<Int>): List<Int?> {
        return originIds.map { originId ->
            getCurrentGroupIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM current_group WHERE origin_id = :originId")
    fun getCurrentGroupIdByOriginId(originId: Int): Int?
}
