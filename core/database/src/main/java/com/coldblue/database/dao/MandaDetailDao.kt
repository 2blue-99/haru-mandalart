package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.MandaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaDetailDao {
    @Query("Select * From manda")
    fun getMandaEntities():  Flow<List<MandaEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaEntities(mandaEntity: List<MandaEntity>)
    @Query("Delete From manda")
    suspend fun deleteMandaEntities()
}