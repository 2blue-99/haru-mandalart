package com.coldblue.domain.todogroup

import com.coldblue.data.repository.todo.CurrentGroupRepository
import java.time.LocalDate
import javax.inject.Inject

class DeleteCurrentGroupUseCase @Inject constructor(
    private val currentGroupRepository: CurrentGroupRepository

) {
    suspend operator fun invoke(currentGroupId: Int, todoGroupId: Int, date: LocalDate) =
        currentGroupRepository.delCurrentGroup(currentGroupId, todoGroupId, date)
}