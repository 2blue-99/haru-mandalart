package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.MandaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaDao {
    @Query("Select * From manda")
    fun getMandaEntities():  Flow<List<MandaEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaEntities(mandaEntities: List<MandaEntity>)
    @Query("Delete From manda")
    suspend fun deleteMandaEntities()
}