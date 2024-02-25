package com.coldblue.data.repo

import com.coldblue.data.mapper.Mapper.asMandaDetailEntity
import com.coldblue.data.mapper.Mapper.asMandaKeyModel
import com.coldblue.data.mapper.Mapper.asMandaKeyEntity
import com.coldblue.database.dao.MandaDetailDao
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaRepoImpl @Inject constructor(
    private val mandaKeyDao: MandaKeyDao,
    private val mandaDetailDao: MandaDetailDao
) : MandaRepo {
    override suspend fun getAllMandaRepository(): Flow<MandaDetail> {
        return mandaKeyDao.getMandaKeyEntities().map { it.map { it.asMandaKeyModel() } }
    }

    override suspend fun upsertKeyMandaRepository(mandaKeys: List<MandaKey>) {
        mandaKeyDao.upsertMandaKeyEntities(mandaKeys.map { it.asMandaKeyEntity() })
    }

    override suspend fun upsertDetailMandaRepository(mandaDetails: List<MandaDetail>) {
        mandaDetailDao.upsertMandaDetailEntities(mandaDetails.map { it.asMandaDetailEntity() })
    }
}