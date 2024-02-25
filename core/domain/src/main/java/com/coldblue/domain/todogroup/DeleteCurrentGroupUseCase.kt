package com.coldblue.domain.todogroup

import com.coldblue.data.repo.CurrentGroupRepository
import javax.inject.Inject

class DeleteCurrentGroupUseCase @Inject constructor(
    private val currentGroupRepository: CurrentGroupRepository

) {
    suspend operator fun invoke(currentGroupId: Int) =
        currentGroupRepository.delCurrentGroup(currentGroupId)
}