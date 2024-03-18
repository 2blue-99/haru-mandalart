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
import kotlinx.coroutines.flow.first
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
    suspend fun deleteCurrentGroupWithTodo(
        currentGroupId: Int,
        todoGroupId: Int,
        updateTime: String,
        date: LocalDate,
    ) {
        deleteCurrentGroup(currentGroupId, updateTime)
        deleteTodoByCurrentGroup(todoGroupId, updateTime, date)
    }

    @Query("UPDATE current_group SET is_del=1, update_time=:updateTime WHERE current_group.id = :currentGroupId")
    suspend fun deleteCurrentGroup(currentGroupId: Int, updateTime: String)

    @Query("UPDATE todo SET todo_group_id=null,update_time=:updateTime WHERE todo_group_id = :todoGroupId AND todo.date = :date")
    suspend fun deleteTodoByCurrentGroup(todoGroupId: Int, updateTime: String, date: LocalDate)


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
    @Query("SELECT * FROM current_group WHERE is_del=0 AND date =:date")
    suspend fun getLatestCurrentGroups(date: LocalDate): List<CurrentGroupEntity>

    @Query("SELECT date FROM current_group WHERE is_del=0 AND date < :date ORDER BY date DESC LIMIT 1")
    suspend fun getCurrentGroupByDate(date: LocalDate): LocalDate?

//    @Query("SELECT * FROM current_group WHERE is_del=0 AND date < :date ORDER BY date DESC LIMIT 1")
//    suspend fun getLatestCurrentGroups(date: LocalDate): List<CurrentGroupEntity>


    @Transaction
    suspend fun setCurrentGroup(date: LocalDate, updateTime: String) {
        val groupCount = getCurrentGroupCount(date)
        if (groupCount > 0) return

        val latestDate = getCurrentGroupByDate(date)
        if (latestDate == null) return

        val latestGroups = getLatestCurrentGroups(latestDate)
        val updatedGroups =
            latestGroups.map {
                CurrentGroupEntity(
                    date = date,
                    updateTime = updateTime,
                    isDel = it.isDel,
                    isSync = it.isSync,
                    index = it.index,
                    originId = it.originId,
                    todoGroupId = it.todoGroupId
                )
            }
        upsertCurrentGroup(updatedGroups)
    }
}
