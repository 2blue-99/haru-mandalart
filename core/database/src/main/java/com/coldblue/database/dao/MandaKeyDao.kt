package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.MandaKeyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaKeyDao {
    @Query("Select * From manda_key")
    fun getMandaKeys(): Flow<List<MandaKeyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaKeys(mandaEntities: List<MandaKeyEntity>)
}