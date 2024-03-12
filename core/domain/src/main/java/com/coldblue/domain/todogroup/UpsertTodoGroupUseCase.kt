package com.coldblue.domain.todogroup

import com.coldblue.data.repository.TodoGroupRepository
import com.coldblue.model.TodoGroup
import javax.inject.Inject

class UpsertTodoGroupUseCase @Inject constructor(
    private val todoGroupRepository: TodoGroupRepository
) {
    suspend operator fun invoke(todoGroup: TodoGroup) = todoGroupRepository.upsertTodoGroup(todoGroup)
}