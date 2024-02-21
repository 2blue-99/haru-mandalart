package com.coldblue.data.repo

import com.coldblue.data.mapper.Mapper.asManda
import com.coldblue.data.mapper.Mapper.asMandaEntity
import com.coldblue.database.dao.MandaDao
import com.coldblue.database.entity.MandaEntity
import com.coldblue.model.Manda
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaRepoImpl @Inject constructor(
    private val mandaDao: MandaDao
): MandaRepo {
    override fun getMandaRepo(): Flow<List<Manda>> =
        mandaDao.getMandaEntities().map { it.map { it.asManda() } }
    override suspend fun upsertMandaRepo(mandaEntities: List<Manda>) {
        mandaDao.upsertMandaEntities(mandaEntities.map { it.asMandaEntity() })
    }
    override suspend fun deleteAllMandaRepo(mandaEntity: Manda) {
        mandaDao.deleteAllManda()
    }
}