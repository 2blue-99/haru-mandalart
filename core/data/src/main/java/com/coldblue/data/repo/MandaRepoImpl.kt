package com.coldblue.data.repo

import com.coldblue.data.mapper.Mapper.asManda
import com.coldblue.data.mapper.Mapper.asMandaEntity
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.model.KeyManda
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaRepoImpl @Inject constructor(
    private val mandaKeyDao: MandaKeyDao
): MandaRepo {
    override fun getMandaRepo(): Flow<List<KeyManda>> =
        mandaKeyDao.getMandaEntities().map { it.map { it.asManda() } }
    override suspend fun upsertMandaRepo(keyMandaEntities: List<KeyManda>) {
        mandaKeyDao.upsertMandaEntities(keyMandaEntities.map { it.asMandaEntity() })
    }
    override suspend fun deleteAllMandaRepo(keyMandaEntity: KeyManda) {
        mandaKeyDao.deleteAllManda()
    }
}