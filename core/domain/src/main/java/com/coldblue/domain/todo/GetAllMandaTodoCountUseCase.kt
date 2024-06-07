package com.coldblue.domain.todo

import co.touchlab.kermit.Logger
import com.coldblue.data.repository.todo.MandaTodoRepository
import com.coldblue.model.MandaTodo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMandaTodoCountUseCase @Inject constructor(
    private val mandaTodoRepository: MandaTodoRepository
) {
    operator fun invoke(): Flow<List<Pair<Int, Int>>> = flow {
        emit(mandaTodoRepository.getAllMandaTodoGraph())
    }
}