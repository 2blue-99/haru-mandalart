package com.coldblue.domain.todogroup

import com.coldblue.data.repo.CurrentGroupRepository
import com.coldblue.data.repo.TodoGroupRepository
import com.coldblue.model.GroupWithCurrent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetGroupWithCurrentUseCase @Inject constructor(
    private val todoGroupRepository: TodoGroupRepository,
    private val currentGroupRepository: CurrentGroupRepository
) {
    operator fun invoke(): Flow<GroupWithCurrent> {
        return todoGroupRepository.getTodoGroup()
            .combine(currentGroupRepository.getCurrentGroup()) { group, current ->
                GroupWithCurrent(group, current)
            }
    }
}