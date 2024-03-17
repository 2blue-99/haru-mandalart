package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.CurrentGroupWithName
import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoGroupEntity
import com.coldblue.database.entity.TodoWithGroupName
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface CurrentGroupDao {
    //    @Query("Select * From current_group")
//    fun getCurrentGroup(date:LocalDate): Flow<List<CurrentGroupEntity>>
//    @Query("SELECT * FROM current_group WHERE date = :date AND is_del=0 OR date = (SELECT MAX(date) FROM current_group WHERE date < :date AND is_del=0)")
//    fun getCurrentGroup(date: LocalDate): Flow<List<CurrentGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrentGroup(currentGroups: List<CurrentGroupEntity>)

    @Transaction
    suspend fun deleteCurrentGroupWithTodo(currentGroupId: Int, todoGroupId: Int) {
        deleteCurrentGroup(currentGroupId)
        deleteTodoByCurrentGroup(todoGroupId)
    }

    @Query("UPDATE current_group SET is_del=1 WHERE id = :currentGroupId")
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

//    @Query("SELECT * FROM current_group WHERE date = :date AND is_del=0")
//    fun getCurrentGroup(date: LocalDate): Flow<List<CurrentGroupEntity>>

    @Query("SELECT current_group.*, todo_group.name AS groupName FROM current_group LEFT JOIN todo_group ON current_group.todo_group_id = todo_group.id WHERE current_group.date=:date AND current_group.is_del=0")
    fun getCurrentGroup(date: LocalDate): Flow<List<CurrentGroupWithName>>


    @Query("SELECT COUNT(*) FROM current_group WHERE date = :date")
    suspend fun getCurrentGroupCount(date: LocalDate): Int

//    @Query("SELECT * FROM current_group WHERE is_del=0 AND date = (SELECT MAX(date) FROM current_group)")
//    suspend fun getLatestCurrentGroups(): List<CurrentGroupEntity>
    @Query("SELECT * FROM current_group WHERE is_del=0 AND date < :date ORDER BY date DESC LIMIT 1")
    suspend fun getLatestCurrentGroups(date: LocalDate): List<CurrentGroupEntity>


    @Transaction
    suspend fun setCurrentGroup(date: LocalDate) {
        val groupCount = getCurrentGroupCount(date)
        if (groupCount == 0) {
            val latestGroups = getLatestCurrentGroups(date)
            val updatedGroups = latestGroups.map { it.copy(date = date, id = 0) }
            upsertCurrentGroup(updatedGroups)
        }
    }
}
