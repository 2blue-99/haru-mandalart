package com.coldblue.domain.todogroup

import com.coldblue.data.repo.CurrentGroupRepository
import com.coldblue.model.CurrentGroup
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentGroupUseCase @Inject constructor(
    private val currentGroupRepository: CurrentGroupRepository
) {
    operator fun invoke(): Flow<Map<Int,CurrentGroup>> = currentGroupRepository.getCurrentGroup()
}