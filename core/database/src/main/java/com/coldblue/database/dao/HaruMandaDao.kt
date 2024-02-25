package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.HaruMandaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HaruMandaDao {
    @Query("Select * From haru_manda")
    fun getHaruMandaEntities():  Flow<List<HaruMandaEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHaruMandaEntities(haruMandaEntities: List<HaruMandaEntity>)
    @Query("Delete From haru_manda")
    suspend fun deleteHaruMandaEntities()
}