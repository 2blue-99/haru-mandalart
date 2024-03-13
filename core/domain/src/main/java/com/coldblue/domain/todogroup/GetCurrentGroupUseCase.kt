package com.coldblue.domain.todogroup

import com.coldblue.data.repository.todo.CurrentGroupRepository
import com.coldblue.model.CurrentGroup
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetCurrentGroupUseCase @Inject constructor(
    private val currentGroupRepository: CurrentGroupRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<CurrentGroup>> = currentGroupRepository.getCurrentGroup(date)
}