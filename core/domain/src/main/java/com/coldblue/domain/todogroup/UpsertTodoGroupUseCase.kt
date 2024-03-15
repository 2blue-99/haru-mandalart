package com.coldblue.domain.todogroup

import com.coldblue.data.repository.todo.TodoGroupRepository
import com.coldblue.model.TodoGroup
import javax.inject.Inject

class UpsertTodoGroupUseCase @Inject constructor(
    private val todoGroupRepository: TodoGroupRepository
) {
    suspend operator fun invoke(todoGroup: TodoGroup) =
        todoGroupRepository.upsertTodoGroup(todoGroup)

    suspend operator fun invoke(todoGroupId: Int, name: String) =
        todoGroupRepository.upsertTodoGroup(todoGroupId, name)
}