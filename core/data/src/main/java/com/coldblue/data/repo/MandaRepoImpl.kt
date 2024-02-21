package com.coldblue.data.repo

import com.coldblue.database.dao.MandaDao
import com.coldblue.database.dao.TodoDao
import com.coldblue.database.entity.MandaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MandaRepoImpl @Inject constructor(
    private val mandaDao: MandaDao
): MandaRepo {
    override fun getMandaRepo(): Flow<List<MandaEntity>> =
        mandaDao.getMandaEntities()
    override suspend fun upsertMandaRepo(mandaEntities: List<MandaEntity>) {
        mandaDao.upsertMandaEntities(mandaEntities)
    }
    override suspend fun deleteMandaRepo() {
        mandaDao.deleteMandaEntities()
    }

}