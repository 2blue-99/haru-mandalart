package com.coldblue.data.repository.manda

import com.coldblue.data.mapper.MandaDetailMapper.asDomain
import com.coldblue.data.mapper.MandaDetailMapper.asEntity
import com.coldblue.data.mapper.MandaDetailMapper.asNetworkModel
import com.coldblue.data.mapper.MandaDetailMapper.asSyncedEntity
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.dao.MandaDetailDao
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.model.MandaDetail
import com.coldblue.network.datasource.MandaDetailDataSource
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaDetailRepositoryImpl @Inject constructor(
    private val mandaDetailDao: MandaDetailDao,
    private val mandaDetailDataSource: MandaDetailDataSource,
    private val syncHelper: SyncHelper,
    private val updateTimeDataSource: UpdateTimeDataSource,
) : MandaDetailRepository {
    override fun getMandaDetails(): Flow<List<MandaDetail>> =
        mandaDetailDao.getMandaDetails().map { it.filter { !it.isDel }.map { it.asDomain() } }

    override suspend fun upsertMandaDetails(mandaDetails: List<MandaDetail>) {
        mandaDetailDao.upsertMandaDetails(mandaDetails.map { it.asEntity() })
        syncHelper.syncWrite()
    }

    override suspend fun deleteMandaDetail(idList: List<Int>) {
        mandaDetailDao.deleteMandaDetails(idList, getUpdateTime())
    }

    override suspend fun deleteAllMandaDetail() {
        mandaDetailDao.deleteAllMandaDetail(getUpdateTime())
    }

    override suspend fun syncRead(): Boolean {
        try {
            val remoteNew =
                mandaDetailDataSource.getMandaDetail(updateTimeDataSource.mandaDetailUpdateTime.first())
            val originIds = remoteNew.map { it.id }

            val todoIds = mandaDetailDao.getMandaDetailIdByOriginIds(originIds)

            val toUpsertMandaDetails = remoteNew.asEntity(todoIds)
            mandaDetailDao.upsertMandaDetails(toUpsertMandaDetails)
            syncHelper.setMaxUpdateTime(
                toUpsertMandaDetails,
                updateTimeDataSource::setMandaDetailUpdateTime
            )
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }

    override suspend fun syncWrite(): Boolean {
        try {
            val localNew =
                mandaDetailDao.getToWriteMandaDetail(updateTimeDataSource.mandaDetailUpdateTime.first())
            val originIds = mandaDetailDataSource.upsertMandaDetail(localNew.asNetworkModel())
            val toUpsertMandaDetails = localNew.asSyncedEntity(originIds)
            mandaDetailDao.upsertMandaDetails(toUpsertMandaDetails)
            syncHelper.setMaxUpdateTime(
                toUpsertMandaDetails,
                updateTimeDataSource::setMandaDetailUpdateTime
            )
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }

}