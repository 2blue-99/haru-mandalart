package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.MandaDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaDetailDao {
    @Query("Select * From manda_detail")
    fun getMandaDetails(): Flow<List<MandaDetailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaDetails(mandaDetailEntity: List<MandaDetailEntity>)

    @Query("Update manda_detail Set is_del = 1 Where id = :idList")
    suspend fun deleteMandaDetails(idList: List<Int>)

    @Query("Update manda_detail Set is_del = 1")
    suspend fun deleteAllMandaDetail()
}