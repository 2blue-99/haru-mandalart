package com.coldblue.data.repo

import com.coldblue.data.mapper.Mapper.asEntity
import com.coldblue.data.mapper.Mapper.asDomain
import com.coldblue.database.dao.MandaDetailDao
import com.coldblue.model.MandaDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaDetailRepositoryImpl @Inject constructor(
    private val mandaDetailDao: MandaDetailDao
) : MandaDetailRepository {
    override fun getMandaDetails(): Flow<List<MandaDetail>> =
        mandaDetailDao.getMandaDetails().map { it.filter { !it.isDel }.map { it.asDomain() } }

    override suspend fun upsertMandaDetails(mandaDetails: List<MandaDetail>) {
        mandaDetailDao.upsertMandaDetails(mandaDetails.map { it.asEntity() })
    }

}