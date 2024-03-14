package com.coldblue.data.mapper

import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.entity.MandaDetailEntity
import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey

object Mapper {
    fun MandaKeyEntity.asDomain(): MandaKey = MandaKey(
        id = id,
        name = name,
        colorIndex = colorIndex
    )

    fun MandaKey.asEntity(): MandaKeyEntity = MandaKeyEntity(
        name = name,
        colorIndex = colorIndex,
        originId = 0,
        isSync = false,
        isDel = false,
        updateTime = getUpdateTime(),
        id = id,
    )

    fun MandaDetailEntity.asDomain(): MandaDetail = MandaDetail(
        name = name,
        isDone = isDone,
        colorIndex = colorIndex,
        id = id
    )

    fun MandaDetail.asEntity(): MandaDetailEntity = MandaDetailEntity(
        name = name,
        isDone = isDone,
        colorIndex = colorIndex,
        originId = 0,
        isSync = false,
        isDel = false,
        updateTime = getUpdateTime(),
        id = id,
    )
}