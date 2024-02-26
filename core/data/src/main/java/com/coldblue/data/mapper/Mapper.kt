package com.coldblue.data.mapper

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
        id = id,
        isSync = false,
        isDel = false,
        updateTime = "updateTime",
        name = name,
        colorIndex = colorIndex
    )


    fun MandaDetailEntity.asDomain(): MandaDetail = MandaDetail(
        id = id,
        mandaId = mandaId,
        name = name,
        isDone = isDone
    )

    fun MandaDetail.asEntity(): MandaDetailEntity = MandaDetailEntity(
        id = id,
        isSync = false,
        isDel = false,
        updateTime = "updateTime",
        mandaId = mandaId,
        name = name,
        isDone = isDone
    )
}