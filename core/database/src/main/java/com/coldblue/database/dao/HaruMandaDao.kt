package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.HaruMandaEntity
import com.coldblue.database.entity.MandaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HaruMandaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHaruMandaEntities(haruMandaEntities: List<HaruMandaEntity>)
    @Query("Select * From haru_manda")
    fun getHaruMandaEntities():  Flow<List<HaruMandaEntity>>
    @Query("Delete From haru_manda")
    fun deleteHaruMandaEntities()
}