package com.coldblue.data.mapper

import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoGroupEntity
import com.coldblue.model.CurrentGroup
import kotlinx.coroutines.flow.Flow


object CurrentGroupMapper {

    fun asEntity(domain: CurrentGroup): CurrentGroupEntity {
        return CurrentGroupEntity(
            isSync = domain.isSync,
            isDel = domain.isDel,
            updateTime = domain.updateTime,
            todoGroupId = domain.todoGroupId,
            id = domain.id,
        )
    }

    fun asEntity(domain: List<CurrentGroup>): List<CurrentGroupEntity> {
        return domain.map { todo ->
            todo.asEntity()
        }
    }

    fun asDomain(entity: Map<TodoGroupEntity, CurrentGroupEntity>): Map<Int, CurrentGroup> {
        return entity.mapValues {
            CurrentGroup(
                isSync = it.value.isSync,
                isDel = it.value.isDel,
                updateTime = it.value.updateTime,
                name = it.key.name,
                todoGroupId = it.value.todoGroupId,
                id = it.value.id,
            )
        }.mapKeys {
            it.value.id
        }
    }
}

fun List<CurrentGroup>.asEntity(): List<CurrentGroupEntity> {
    return CurrentGroupMapper.asEntity(this)
}

fun CurrentGroup.asEntity(): CurrentGroupEntity {
    return CurrentGroupMapper.asEntity(this)
}

fun Map<TodoGroupEntity, CurrentGroupEntity>.asDomain(): Map<Int, CurrentGroup> {
    return CurrentGroupMapper.asDomain(this)
}

