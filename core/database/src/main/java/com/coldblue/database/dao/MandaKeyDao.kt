package com.coldblue.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldblue.database.entity.MandaKeyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaKeyDao {
    @Query("Select * From manda_key")
    fun getMandaKeys(): Flow<List<MandaKeyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaKeys(mandaEntities: List<MandaKeyEntity>)

    @Query("Update manda_key Set is_del = 1 Where id = :idList")
    suspend fun deleteMandaKeys(idList: List<Int>)

    @Query("Update manda_key Set is_del = 1")
    suspend fun deleteAllMandaKey()


    @Transaction
    fun getMandaKeyIdByOriginIds(originIds: List<Int>): List<Int?> {
        return originIds.map { originId ->
            getMandaKeyIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM manda_key WHERE origin_id = :originId")
    fun getMandaKeyIdByOriginId(originId: Int): Int?
}