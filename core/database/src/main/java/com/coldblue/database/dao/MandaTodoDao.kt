package com.coldblue.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.coldblue.database.entity.MandaTodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaTodoDao {
    @Query("SELECT * FROM manda_todo WHERE is_del = 0")
    fun getMandaTodo(): Flow<List<MandaTodoEntity>>


    @Transaction
    fun getMandaTodoIdByOriginIds(originIds: List<Int>): List<Int?> {
        return originIds.map { originId ->
            getMandaTodoIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM manda_todo WHERE origin_id = :originId")
    fun getMandaTodoIdByOriginId(originId: Int): Int?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaTodo(mandaTodo: MandaTodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaTodo(mandaTodo: List<MandaTodoEntity>)

    @Query("SELECT * FROM manda_todo WHERE update_time > :updateTime AND is_sync=0")
    fun getToWriteMandaTodos(updateTime: String): List<MandaTodoEntity>
}