package com.coldblue.data.mapper

import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.model.MandaKey
import com.coldblue.model.UpdateNote
import com.coldblue.network.model.NetWorkUpdateNote
import com.coldblue.network.model.NetworkMandaKey

object MandaKeyMapper {

    fun MandaKeyEntity.asDomain(): MandaKey = MandaKey(
        name = name,
        colorIndex = colorIndex,
        originId = originId,
        id = id
    )

    fun MandaKey.asEntity(): MandaKeyEntity = MandaKeyEntity(
        name = name,
        colorIndex = colorIndex,
        isSync = false,
        isDel = false,
        updateTime = getUpdateTime(),
        originId = originId,
        id = id,
    )

    fun NetWorkUpdateNote.asEntity(): UpdateNote = UpdateNote(
        updateTime = this.update_time,
        updateContent = this.update_content
    )

    fun List<NetworkMandaKey>.asEntity(mandaKeyIds: List<Int?>): List<MandaKeyEntity> {
        return this.mapIndexed { index, mandaKey ->
            MandaKeyEntity(
                originId = mandaKey.id,
                isSync = true,
                isDel = mandaKey.is_del,
                updateTime = mandaKey.update_time,
                colorIndex = mandaKey.color_index,
                name = mandaKey.name,
                id = mandaKeyIds[index] ?: mandaKey.manda_index,
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
                manda_index = it.id,
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