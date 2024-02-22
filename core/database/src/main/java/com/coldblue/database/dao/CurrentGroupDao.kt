package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.MandaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentGroupDao {
    @Query("Select * From current_group")
    fun getCurrentGroupEntities():  Flow<List<CurrentGroupEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrentGroupEntities(currentGroupEntities: List<CurrentGroupEntity>)
    @Query("Delete From current_group")
    suspend fun deleteCurrentGroupEntities()
}