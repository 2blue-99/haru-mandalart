package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.CurrentGroupWithName
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface CurrentGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroupEntity)

    @Transaction
    suspend fun syncWriteCurrentTGroup(groups: List<CurrentGroupEntity>) {
        groups.forEach { group ->
            upsertCurrentGroupOneSync(group)
        }
    }

    @Query("SELECT * FROM current_group WHERE date = :date AND current_group.`index` = :index")
    suspend fun getGroupByDateAndIndex(date: LocalDate, index: Int): CurrentGroupEntity?

    @Transaction
    suspend fun upsertCurrentGroupOne(currentGroup: CurrentGroupEntity) {
        val existingGroup = getGroupByDateAndIndex(currentGroup.date, currentGroup.index)
        if (existingGroup != null) {
            upsertCurrentGroup(currentGroup.copy(id = existingGroup.id, originId = existingGroup.originId))
        } else {
            upsertCurrentGroup(currentGroup)
        }
    }

    @Transaction
    suspend fun upsertCurrentGroupOneSync(currentGroup: CurrentGroupEntity) {
        val existingGroup = getGroupByDateAndIndex(currentGroup.date, currentGroup.index)
        if (existingGroup != null) {
            upsertCurrentGroup(currentGroup.copy(id = existingGroup.id))
        } else {
            upsertCurrentGroup(currentGroup)
        }

    }

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

    @Query("UPDATE current_group SET is_del=1, update_time=:updateTime ,is_sync = 0 WHERE current_group.id = :currentGroupId")
    suspend fun deleteCurrentGroup(currentGroupId: Int, updateTime: String)

    @Query("UPDATE todo SET todo_group_id=null,update_time=:updateTime ,is_sync = 0 WHERE todo_group_id = :todoGroupId AND todo.date = :date")
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


    @Query("SELECT current_group.*, todo_group.name AS groupName FROM current_group LEFT JOIN todo_group ON current_group.todo_group_id = todo_group.id WHERE current_group.date=:date AND current_group.is_del=0")
    fun getCurrentGroup(date: LocalDate): Flow<List<CurrentGroupWithName>>

}
