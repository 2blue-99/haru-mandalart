package com.coldblue.data.mapper

import com.coldblue.database.entity.MandaDetailEntity
import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import java.time.LocalDateTime

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
        updateTime = LocalDateTime.now().toString(),
        name = name,
        colorIndex = colorIndex
    )


    fun MandaDetailEntity.asDomain(): MandaDetail = MandaDetail(
        name = name,
        isDone = isDone,
        colorIndex = colorIndex,
        id = id
    )

    fun MandaDetail.asEntity(): MandaDetailEntity = MandaDetailEntity(
        isSync = false,
        isDel = false,
        updateTime = "updateTime",
        name = name,
        isDone = isDone,
        colorIndex = colorIndex,
        id = id,
    )
}