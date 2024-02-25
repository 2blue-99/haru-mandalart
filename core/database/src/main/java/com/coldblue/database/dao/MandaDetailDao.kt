package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.MandaKeyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaDetailDao {
    @Query("Select * From manda_detail")
    fun getMandaEntities(): Flow<List<MandaKeyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaEntities(mandaKeyEntity: List<MandaKeyEntity>)
}