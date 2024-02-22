package com.coldblue.data.mapper

import com.coldblue.database.entity.MandaEntity
import com.coldblue.database.entity.TodoEntity
import com.coldblue.model.Manda
import com.coldblue.model.Todo

object Mapper {
    fun MandaEntity.asManda(): Manda = Manda(id, isSync, isDel, updateTime, name, color)

    fun Manda.asMandaEntity(): MandaEntity = MandaEntity(id, isSync, isDel, updateTime, name, color)

    fun TodoEntity.asTodo(): Todo = Todo(id, originId, haruMandaId, isSync, isDel, updateTime, title, content, time)

    fun Todo.asTodoEntity(): TodoEntity = TodoEntity(id, originId, harumandaId, isSync, isDel, updateTime, title, content, time)
}