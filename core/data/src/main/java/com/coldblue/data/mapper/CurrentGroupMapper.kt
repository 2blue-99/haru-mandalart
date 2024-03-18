package com.coldblue.data.mapper

import com.coldblue.data.util.getUpdateTime
import com.coldblue.data.util.toDate
import com.coldblue.database.entity.CurrentGroupEntity
import com.coldblue.database.entity.CurrentGroupWithName
import com.coldblue.database.entity.TodoGroupEntity
import com.coldblue.model.CurrentGroup
import com.coldblue.network.model.NetWorkTodoGroup
import com.coldblue.network.model.NetworkCurrentGroup
import java.time.LocalDate


object CurrentGroupMapper {

    fun asEntity(domain: CurrentGroup): CurrentGroupEntity {
        return CurrentGroupEntity(
            isSync = false,
            isDel = domain.isDel,
            updateTime = getUpdateTime(),
            todoGroupId = domain.todoGroupId,
            date = domain.date,
            index = domain.index,
            originId = domain.originId,
            originGroupId = domain.originGroupId,
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
            index = entity.index,
            date = entity.date,
            originGroupId = entity.originGroupId,
            originId = entity.originId,
            todoGroupId = entity.todoGroupId,
        )
    }

    fun List<NetworkCurrentGroup>.asEntity(todoGroupIds: List<Int?>): List<CurrentGroupEntity> {
        return this.mapIndexed { index, networkCurrentGroup ->
            CurrentGroupEntity(
                originId = networkCurrentGroup.id,
                originGroupId = networkCurrentGroup.orgin_group_id,
                date = networkCurrentGroup.date.toDate(),
                isSync = true,
                isDel = networkCurrentGroup.is_del,
                updateTime = networkCurrentGroup.update_time,
                index = networkCurrentGroup.index,
                todoGroupId = networkCurrentGroup.todo_group_id,
                id = todoGroupIds[index] ?: 0,
            )
        }
    }

    fun List<CurrentGroupEntity>.asNetworkModel(): List<NetworkCurrentGroup> {
        return this.map {
            NetworkCurrentGroup(
                date = it.date.toString(),
                index = it.index,
                todo_group_id = it.todoGroupId,
                update_time = it.updateTime,
                is_del = it.isDel,
                id = it.originId,
                orgin_group_id = it.originGroupId
            )
        }
    }

    fun List<CurrentGroupEntity>.asSyncedEntity(originIds: List<Int>): List<CurrentGroupEntity> {
        return this.mapIndexed { index, entity ->
            CurrentGroupEntity(
                originId = originIds[index],
                isSync = true,
                isDel = entity.isDel,
                updateTime = entity.updateTime,
                date = entity.date,
                index = entity.index,
                todoGroupId = entity.todoGroupId,
                originGroupId = entity.originGroupId,
                id = entity.id,
            )
        }
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

