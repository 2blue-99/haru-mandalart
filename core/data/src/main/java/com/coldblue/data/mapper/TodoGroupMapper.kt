package com.coldblue.data.mapper

import com.coldblue.database.entity.TodoGroupEntity
import com.coldblue.model.TodoGroup
import java.time.LocalDate

object TodoGroupMapper : EntityMapper<TodoGroup, TodoGroupEntity> {

    override fun asEntity(domain: TodoGroup): TodoGroupEntity {
        return TodoGroupEntity(
            originId = 0,
            isSync = false,
            isDel = domain.isDel,
            updateTime = LocalDate.now().toString(),
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
            isDel = entity.isDel,
            originId = entity.originId,
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