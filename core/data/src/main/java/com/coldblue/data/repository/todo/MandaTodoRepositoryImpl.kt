package com.coldblue.data.repository.todo

import com.coldblue.data.mapper.MandaTodoMapper.asDomain
import com.coldblue.data.mapper.MandaTodoMapper.asEntity
import com.coldblue.data.sync.SyncHelper
import com.coldblue.database.dao.MandaTodoDao
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.model.MandaTodo
import com.coldblue.network.datasource.MandaTodoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class MandaTodoRepositoryImpl @Inject constructor(
    private val mandaTodoDao: MandaTodoDao,
    private val mandaTodoDataSource: MandaTodoDataSource,
    private val syncHelper: SyncHelper,
    private val updateTimeDataSource: UpdateTimeDataSource,
) : MandaTodoRepository {
    override suspend fun upsertMandaTodo(mandaTodo: MandaTodo) {
        mandaTodoDao.upsertMandaTodo(mandaTodo.asEntity())
    }

    override fun getMandaTodo(): Flow<List<MandaTodo>> {
        return mandaTodoDao.getMandaTodo().map { it.asDomain() }
    }

    override suspend fun syncRead(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun syncWrite(): Boolean {
        TODO("Not yet implemented")
    }

}
