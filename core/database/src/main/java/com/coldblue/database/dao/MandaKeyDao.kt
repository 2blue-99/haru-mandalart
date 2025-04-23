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
    @Query("Select * From manda_key Where is_del = 0")
    fun getMandaKeys(): Flow<List<MandaKeyEntity>>

    @Query("SELECT * FROM manda_key WHERE id = 5 AND is_del = 0")
    fun getFinalManda(): Flow<MandaKeyEntity?>

    @Query("SELECT * FROM manda_key WHERE update_time > :updateTime AND is_sync=0")
    fun getToWriteMandaKeys(updateTime: String): List<MandaKeyEntity>

    @Transaction
    fun getMandaKeyIdByOriginIds(originIds: List<Int>): List<Int?> {
        return originIds.map { originId ->
            getMandaKeyIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM manda_key WHERE origin_id = :originId")
    fun getMandaKeyIdByOriginId(originId: Int): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaKeys(mandaEntities: List<MandaKeyEntity>)

    @Transaction
    suspend fun deleteMandaKeyAndDetail(
        date: String,
        keyIdList: List<Int>,
        detailIdList: List<Int>
    ) {
        deleteMandaKeys(date, keyIdList)
        deleteMandaDetails(date, detailIdList)
        deleteMandaTodos(date, keyIdList[0] - 1)
    }

    @Query("Update manda_key Set is_del = 1, is_Sync = 0, update_time = :date Where id in (:idList)")
    suspend fun deleteMandaKeys(date: String, idList: List<Int>)

    @Query("Update manda_todo Set is_del = 1, is_Sync = 0, update_time = :date Where manda_index = :id")
    suspend fun deleteMandaTodos(date: String, id: Int)

    @Query("Update manda_detail Set is_del = 1, is_Sync = 0, update_time = :date Where id in (:idList)")
    suspend fun deleteMandaDetails(date: String, idList: List<Int>)


    @Transaction
    suspend fun deleteManda(
        date: String,
        keyIdList: List<Int>,
        detailIdList: List<Int>

    ) {
        deleteMandaKeys(date, keyIdList)
        deleteMandaDetails(date, detailIdList)
        deleteMandaTodos(date, keyIdList)
    }

    @Query("Update manda_todo Set is_del = 1, is_Sync = 0, update_time = :date Where manda_index in (:idList)")
    suspend fun deleteMandaTodos(date: String, idList: List<Int>)

    @Query("Update manda_key Set is_del = 1, is_Sync = 0, update_time = :date")
    suspend fun deleteAllMandaKey(date:String)
}