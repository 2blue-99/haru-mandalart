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

class UpdateNoteRepositoryImpl @Inject constructor(
    private val getUpdateNoteDataSource: UpdateNoteDataSource
    ) : UpdateNoteRepository {
    override suspend fun getUpdateNote(): UpdateNote =
        getUpdateNoteDataSource.getUpdateNote().asEntity()
}