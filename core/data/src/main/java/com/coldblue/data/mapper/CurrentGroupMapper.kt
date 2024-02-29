package com.coldblue.data.mapper

import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.TodoGroupEntity
import com.coldblue.model.CurrentGroup
import java.time.LocalDate


object CurrentGroupMapper {

    fun asEntity(domain: CurrentGroup): CurrentGroupEntity {
        return CurrentGroupEntity(
            isSync = false,
            isDel = domain.isDel,
            updateTime = LocalDate.now().toString(),
            todoGroupId = domain.todoGroupId,
            id = domain.id,
        )
    }

    fun asEntity(domain: List<CurrentGroup>): List<CurrentGroupEntity> {
        return domain.map { todo ->
            todo.asEntity()
        }
    }

    fun asDomain(entity: List<CurrentGroupEntity>): List<CurrentGroup> {
        return entity.map { it.asDomain() }
    }

    fun asDomain(entity: CurrentGroupEntity): CurrentGroup {
        return CurrentGroup(
            id = entity.id,
            todoGroupId = entity.todoGroupId
        )
    }
}

fun List<CurrentGroup>.asEntity(): List<CurrentGroupEntity> {
    return CurrentGroupMapper.asEntity(this)
}

fun CurrentGroup.asEntity(): CurrentGroupEntity {
    return CurrentGroupMapper.asEntity(this)
}

fun CurrentGroupEntity.asDomain(): CurrentGroup {
    return CurrentGroupMapper.asDomain(this)
}

fun List<CurrentGroupEntity>.asDomain(): List<CurrentGroup> {
    return CurrentGroupMapper.asDomain(this)
}

