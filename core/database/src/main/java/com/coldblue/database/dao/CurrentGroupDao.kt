package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentGroupDao {
    @Query("Select * From todo_group JOIN current_group ON todo_group.id = current_group.todo_group_id")
    fun getCurrentGroup(): Flow<Map<TodoGroupEntity, CurrentGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroupEntity)

    @Query("Delete From current_group WHERE id = :currentGroupId")
    suspend fun deleteCurrentGroup(currentGroupId: Int)
}