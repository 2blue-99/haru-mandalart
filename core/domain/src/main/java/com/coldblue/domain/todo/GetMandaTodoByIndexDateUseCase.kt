package com.coldblue.domain.todo

import com.coldblue.data.repository.todo.MandaTodoRepository
import com.coldblue.model.MandaTodo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMandaTodoByIndexDateUseCase @Inject constructor(
    private val mandaTodoRepository: MandaTodoRepository
) {
    operator fun invoke(index: Int, date: String): Flow<List<MandaTodo>> =
        mandaTodoRepository.getMandaTodoByIndexDate(index, date)
}