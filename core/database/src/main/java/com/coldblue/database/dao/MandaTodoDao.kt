package com.coldblue.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldblue.database.entity.MandaTodoEntity
import com.coldblue.database.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaTodoDao {
    @Query("SELECT * FROM manda_todo WHERE is_del = 0")
    fun getMandaTodo(): Flow<List<MandaTodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMandaTodo(mandaTodo: MandaTodoEntity)
}