package com.coldblue.data.repository.manda

import com.coldblue.data.mapper.MandaKeyMapper.asDomain
import com.coldblue.data.mapper.MandaKeyMapper.asEntity
import com.coldblue.data.mapper.MandaKeyMapper.asNetworkModel
import com.coldblue.data.mapper.MandaKeyMapper.asSyncedEntity
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.datastore.UserDataSource
import com.coldblue.model.MandaKey
import com.coldblue.model.UpdateNote
import com.coldblue.network.datasource.MandaKeyDataSource
import com.coldblue.network.datasource.UpdateNoteDataSource
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaKeyRepositoryImpl @Inject constructor(
    private val syncHelper: SyncHelper,
    private val userDataSource: UserDataSource,
    private val mandaKeyDao: MandaKeyDao,
    private val mandaKeyDataSource: MandaKeyDataSource,
    private val updateTimeDataSource: UpdateTimeDataSource,
    private val getUpdateNoteDataSource: UpdateNoteDataSource
    ) : MandaKeyRepository {
    override fun getMandaKeys(): Flow<List<MandaKey>> =
        mandaKeyDao.getMandaKeys().map { it.map { it.asDomain() } }

    override suspend fun getUpdateNote(): UpdateNote =
        getUpdateNoteDataSource.getUpdateNote().last().asEntity()

    override suspend fun upsertMandaKeys(mandaKeys: List<MandaKey>) {
        mandaKeyDao.upsertMandaKeys(mandaKeys.map { it.asEntity() })
        syncHelper.syncWrite()
    }

    override suspend fun deleteMandaKeys(keyIdList: List<Int>, detailIdList: List<Int>) {
        mandaKeyDao.deleteMandaKeyAndDetail(getUpdateTime(), keyIdList, detailIdList)
        syncHelper.syncWrite()
    }

    override suspend fun deleteAllMandaDetail() {
        mandaKeyDao.deleteAllMandaKey()
    }

    override fun isInit(): Flow<Boolean> {
        return mandaKeyDao.getFinalManda().map { it != null }
    }

    override suspend fun syncRead(): Boolean {
        try {
            val remoteNew =
                mandaKeyDataSource.getMandaKey(updateTimeDataSource.mandaKeyUpdateTime.first())
            val originIds = remoteNew.map { it.id }

            val mandaKeyIds = mandaKeyDao.getMandaKeyIdByOriginIds(originIds)

            val toUpsertMandaKeys = remoteNew.asEntity(mandaKeyIds)

            if(toUpsertMandaKeys.map { it.id }.contains(5))
                userDataSource.updateMandaInitState(true)

            mandaKeyDao.upsertMandaKeys(toUpsertMandaKeys)

            syncHelper.setMaxUpdateTime(
                toUpsertMandaKeys,
                updateTimeDataSource::setMandaKeyUpdateTime
            )
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }

    override suspend fun syncWrite(): Boolean {
        try {
            val localNew = mandaKeyDao.getToWriteMandaKeys(updateTimeDataSource.mandaKeyUpdateTime.first())
            val originIds = mandaKeyDataSource.upsertMandaKey(localNew.asNetworkModel())

            val toUpsertMandaKeys = localNew.asSyncedEntity(originIds)
            mandaKeyDao.upsertMandaKeys(toUpsertMandaKeys)

            syncHelper.setMaxUpdateTime(
                toUpsertMandaKeys,
                updateTimeDataSource::setMandaKeyUpdateTime
            )
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }



}