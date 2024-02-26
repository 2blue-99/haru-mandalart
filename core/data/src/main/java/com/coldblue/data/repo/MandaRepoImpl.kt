package com.coldblue.data.repo

import com.coldblue.data.mapper.Mapper.asMandaDetailEntity
import com.coldblue.data.mapper.Mapper.asMandaDetailModel
import com.coldblue.data.mapper.Mapper.asMandaKeyModel
import com.coldblue.data.mapper.Mapper.asMandaKeyEntity
import com.coldblue.database.dao.MandaDetailDao
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.model.MandaCombine
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MandaRepoImpl @Inject constructor(
    private val mandaKeyDao: MandaKeyDao,
    private val mandaDetailDao: MandaDetailDao
) : MandaRepo {
    override fun getAllMandaRepository(): Flow<MandaCombine> =
        mandaKeyDao.getMandaKeys()
            .combine(mandaDetailDao.getMandaDetails()) { mandaKeys, mandaDetails ->
                MandaCombine(
                    mandaKeys = mandaKeys.filter { !it.isDel }.map { it.asMandaKeyModel() },
                    mandaDetails = mandaDetails.filter { !it.isDel }.map { it.asMandaDetailModel() })
            }

    override suspend fun upsertMandaKeysRepository(mandaKeys: List<MandaKey>) {
        mandaKeyDao.upsertMandaKeys(mandaKeys.map { it.asMandaKeyEntity() })
    }

    override suspend fun upsertMandaDetailsRepository(mandaDetails: List<MandaDetail>) {
        mandaDetailDao.upsertMandaDetails(mandaDetails.map { it.asMandaDetailEntity() })
    }
}