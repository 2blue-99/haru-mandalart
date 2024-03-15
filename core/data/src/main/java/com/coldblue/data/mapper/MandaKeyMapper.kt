package com.coldblue.data.mapper

import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.network.model.NetworkMandaKey

object MandaKeyMapper {
    fun List<NetworkMandaKey>.asEntity(mandaDetailIds: List<Int?>): List<MandaKeyEntity> {
        return this.mapIndexed { index, mandaKey ->
            MandaKeyEntity(
                originId = mandaKey.id,
                isSync = true,
                isDel = mandaKey.is_del,
                updateTime = mandaKey.update_time,
                colorIndex = mandaKey.color_index,
                name = mandaKey.name,
                id = mandaDetailIds[index] ?: 0,
            )
        }
    }
    fun List<MandaKeyEntity>.asNetworkModel(): List<NetworkMandaKey> {
        return this.map {
            NetworkMandaKey(
                color_index = it.colorIndex,
                name = it.name,
                update_time = it.updateTime,
                is_del = it.isDel,
                id = it.originId
            )
        }
    }
    fun List<MandaKeyEntity>.asSyncedEntity(originIds: List<Int>): List<MandaKeyEntity> {
        return this.mapIndexed { index, entity ->
            MandaKeyEntity(
                colorIndex = entity.colorIndex,
                name = entity.name,
                originId = originIds[index],
                isSync = true,
                isDel = entity.isDel,
                updateTime = entity.updateTime,
                id = entity.id,
            )
        }
    }
}