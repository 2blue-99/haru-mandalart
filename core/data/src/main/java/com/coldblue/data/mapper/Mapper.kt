package com.coldblue.data.mapper

import com.coldblue.database.entity.MandaDetailEntity
import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.database.entity.TodoEntity
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.Todo

object Mapper {
    fun MandaKeyEntity.asMandaKeyModel(): MandaKey = MandaKey(
        id = id,
        name = name,
        colorIndex = colorIndex
    )

    fun MandaKey.asMandaKeyEntity(): MandaKeyEntity = MandaKeyEntity(
        id = id,
        isSync = false,
        isDel = false,
        updateTime = "updateTime",
        name = name,
        colorIndex = colorIndex
    )


    fun MandaDetailEntity.asMandaDetailModel(): MandaDetail = MandaDetail(
        id = id,
        mandaId = mandaId,
        name = name,
        isDone = isDone
    )

    fun MandaDetail.asMandaDetailEntity(): MandaDetailEntity = MandaDetailEntity(
        id = id,
        isSync = false,
        isDel = false,
        updateTime = "updateTime",
        mandaId = mandaId,
        name = name,
        isDone = isDone
    )


    fun TodoEntity.asTodo(): Todo =
        Todo(id, originId, haruMandaId, isSync, isDel, updateTime, title, content, time)

    fun Todo.asTodoEntity(): TodoEntity =
        TodoEntity(id, originId, harumandaId, isSync, isDel, updateTime, title, content, time)
}