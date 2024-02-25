package com.coldblue.data.mapper

import com.coldblue.database.entity.MandaKeyEntity
import com.coldblue.database.entity.TodoEntity
import com.coldblue.model.KeyManda
import com.coldblue.model.Todo

object Mapper {
    fun MandaKeyEntity.asManda(): KeyManda = KeyManda(id, isSync, isDel, updateTime, name, colorIndex)

    fun KeyManda.asMandaEntity(): MandaKeyEntity = MandaKeyEntity(id, isSync, isDel, updateTime, name, color)

    fun TodoEntity.asTodo(): Todo = Todo(id, originId, haruMandaId, isSync, isDel, updateTime, title, content, time)

    fun Todo.asTodoEntity(): TodoEntity = TodoEntity(id, originId, harumandaId, isSync, isDel, updateTime, title, content, time)
}