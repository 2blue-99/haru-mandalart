package com.coldblue.data.mapper

import com.coldblue.database.entity.TodoGroupEntity
import com.coldblue.model.TodoGroup

object TodoGroupMapper : EntityMapper<TodoGroup, TodoGroupEntity> {

    override fun asEntity(domain: TodoGroup): TodoGroupEntity {
        return TodoGroupEntity(
            originId = domain.originId,
            isSync = domain.isSync,
            isDel = domain.isDel,
            updateTime = domain.updateTime,
            name = domain.name,
            id = domain.id,
        )
    }

    fun asEntity(domain: List<TodoGroup>): List<TodoGroupEntity> {
        return domain.map { todo ->
            todo.asEntity()
        }
    }

    override fun asDomain(entity: TodoGroupEntity): TodoGroup {
        return TodoGroup(
            originId = entity.originId,
            isSync = entity.isSync,
            isDel = entity.isDel,
            updateTime = entity.updateTime,
            name = entity.name,
            id = entity.id,
        )
    }

    fun asDomain(entity: List<TodoGroupEntity>): List<TodoGroup> {
        return entity.map { todoEntity ->
            todoEntity.asDomain()
        }
    }
}

fun List<TodoGroup>.asEntity(): List<TodoGroupEntity> {
    return TodoGroupMapper.asEntity(this)
}

fun TodoGroup.asEntity(): TodoGroupEntity {
    return TodoGroupMapper.asEntity(this)
}

fun List<TodoGroupEntity>.asDomain(): List<TodoGroup> {
    return TodoGroupMapper.asDomain(this)
}

fun TodoGroupEntity.asDomain(): TodoGroup {
    return TodoGroupMapper.asDomain(this)
}