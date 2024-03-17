package com.coldblue.data.mapper

import com.coldblue.database.entity.CurrentGroupWithName
import com.coldblue.model.CurrentGroup

object CurrentGroupWithNameMapper {


}
fun List<CurrentGroupWithName>.asDomain(): List<CurrentGroup> {
    return this.mapIndexed { index, entity ->
        CurrentGroup(
            isDel = entity.currentGroup.isDel,
            date = entity.currentGroup.date,
            index = entity.currentGroup.index,
            todoGroupId = entity.currentGroup.todoGroupId,
            id = entity.currentGroup.id,
            name = entity.groupName
        )
    }
}