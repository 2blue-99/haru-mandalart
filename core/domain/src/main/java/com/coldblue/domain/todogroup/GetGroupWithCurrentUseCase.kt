package com.coldblue.domain.todogroup

import com.coldblue.data.repository.todo.CurrentGroupRepository
import com.coldblue.data.repository.todo.TodoGroupRepository
import com.coldblue.model.GroupWithCurrent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetGroupWithCurrentUseCase @Inject constructor(
    private val todoGroupRepository: TodoGroupRepository,
    private val currentGroupRepository: CurrentGroupRepository
) {
    operator fun invoke(date:LocalDate): Flow<GroupWithCurrent> {
        return todoGroupRepository.getTodoGroup()
            .combine(currentGroupRepository.getCurrentGroup(date)) { group, current ->
                GroupWithCurrent(group, current)
            }
    }
}