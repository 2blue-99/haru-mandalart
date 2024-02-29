package com.coldblue.model

import com.coldblue.model.CurrentGroup
import com.coldblue.model.TodoGroup

data class GroupWithCurrent(
    val todoGroupList: List<TodoGroup>,
    val currentGroupList: List<CurrentGroup>,
)

