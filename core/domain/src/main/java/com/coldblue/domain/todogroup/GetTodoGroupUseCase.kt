package com.coldblue.domain.todogroup

import com.coldblue.data.repository.TodoGroupRepository
import com.coldblue.model.TodoGroup
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoGroupUseCase @Inject constructor(
    private val todoGroupRepository: TodoGroupRepository
) {
    operator fun invoke(): Flow<List<TodoGroup>> = todoGroupRepository.getTodoGroup()
}