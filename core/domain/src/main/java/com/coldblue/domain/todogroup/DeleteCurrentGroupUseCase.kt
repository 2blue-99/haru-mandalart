package com.coldblue.domain.todogroup

import com.coldblue.data.repository.CurrentGroupRepository
import javax.inject.Inject

class DeleteCurrentGroupUseCase @Inject constructor(
    private val currentGroupRepository: CurrentGroupRepository

) {
    suspend operator fun invoke(currentGroupId: Int, todoGroupId: Int) =
        currentGroupRepository.delCurrentGroup(currentGroupId,todoGroupId)
}