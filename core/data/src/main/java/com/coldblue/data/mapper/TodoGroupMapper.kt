package com.coldblue.data.mapper

import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.entity.TodoGroupEntity
import com.coldblue.model.TodoGroup
import com.coldblue.network.model.NetWorkTodoGroup
import com.orhanobut.logger.Logger

object TodoGroupMapper : EntityMapper<TodoGroup, TodoGroupEntity> {

    override fun asEntity(domain: TodoGroup): TodoGroupEntity {
        return TodoGroupEntity(
            originId = domain.originId,
            isSync = false,
            isDel = domain.isDel,
            updateTime = getUpdateTime(),
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

    fun List<NetWorkTodoGroup>.asEntity(todoGroupIds: List<Int?>): List<TodoGroupEntity> {
        return this.mapIndexed { index, netWorkTodoGroup ->
            TodoGroupEntity(
                originId = netWorkTodoGroup.id,
                name = netWorkTodoGroup.name,
                isSync = true,
                isDel = netWorkTodoGroup.is_del,
                updateTime = netWorkTodoGroup.update_time,
                id = todoGroupIds[index] ?: 0,
            )
        }
    }

    fun List<TodoGroupEntity>.asNetworkModel(): List<NetWorkTodoGroup> {
        return this.map {
            NetWorkTodoGroup(
                name = it.name,
                update_time = it.updateTime,
                is_del = it.isDel,
                id = it.originId
            )
        }
    }

    fun List<TodoGroupEntity>.asSyncedEntity(originIds: List<Int>): List<TodoGroupEntity> {
        return this.mapIndexed { index, entity ->
            TodoGroupEntity(
                originId = originIds[index],
                isSync = true,
                isDel = entity.isDel,
                updateTime = entity.updateTime,
                name = entity.name,
                id = entity.id,
            )
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