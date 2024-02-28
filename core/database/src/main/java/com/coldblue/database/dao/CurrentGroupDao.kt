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
    @Query("Select * From current_group")
    fun getCurrentGroup(): Flow<List<CurrentGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroupEntity)

    @Query("Delete From current_group WHERE id = :currentGroupId")
    suspend fun deleteCurrentGroup(currentGroupId: Int)
}