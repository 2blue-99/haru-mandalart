package com.coldblue.domain.todogroup

import com.coldblue.data.repository.todo.CurrentGroupRepository
import com.coldblue.model.CurrentGroup
import javax.inject.Inject

class UpsertCurrentGroupUseCase @Inject constructor(
    private val currentGroupRepository: CurrentGroupRepository
) {
    suspend operator fun invoke(currentGroup: CurrentGroup) = currentGroupRepository.upsertCurrentGroup(currentGroup)
}