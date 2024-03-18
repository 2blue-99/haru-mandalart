package com.coldblue.data.mapper

import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.entity.MandaDetailEntity
import com.coldblue.model.MandaDetail
import com.coldblue.network.model.NetworkMandaDetail

object MandaDetailMapper {

    fun MandaDetailEntity.asDomain(): MandaDetail = MandaDetail(
        name = name,
        isDone = isDone,
        colorIndex = colorIndex,
        originId = originId,
        id = id
    )

    fun MandaDetail.asEntity(): MandaDetailEntity = MandaDetailEntity(
        name = name,
        isDone = isDone,
        colorIndex = colorIndex,
        isSync = false,
        isDel = false,
        updateTime = getUpdateTime(),
        originId = originId,
        id = id,
    )

    fun List<NetworkMandaDetail>.asEntity(mandaDetailIds: List<Int?>): List<MandaDetailEntity> {
        return this.mapIndexed { index, mandaDetail ->
            MandaDetailEntity(
                originId = mandaDetail.id,
                isSync = true,
                isDel = mandaDetail.is_del,
                updateTime = mandaDetail.update_time,
                colorIndex = mandaDetail.color_index,
                isDone = mandaDetail.is_done,
                name = mandaDetail.name,
                id = mandaDetailIds[index] ?: mandaDetail.manda_index,
            )
        }
    }

    fun List<MandaDetailEntity>.asNetworkModel(): List<NetworkMandaDetail> {
        return this.map {
            NetworkMandaDetail(
                color_index = it.colorIndex,
                name = it.name,
                is_done = it.isDone,
                update_time = it.updateTime,
                is_del = it.isDel,
                manda_index = it.id,
                id = it.originId
            )
        }
    }

    fun List<MandaDetailEntity>.asSyncedEntity(originIds: List<Int>): List<MandaDetailEntity> {
        return this.mapIndexed { index, entity ->
            MandaDetailEntity(
                colorIndex = entity.colorIndex,
                name = entity.name,
                isDone = entity.isDone,
                originId = originIds[index],
                isSync = true,
                isDel = entity.isDel,
                updateTime = entity.updateTime,
                id = entity.id,
            )
        }
    }
}