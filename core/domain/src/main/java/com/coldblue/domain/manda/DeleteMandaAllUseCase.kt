package com.coldblue.domain.manda

import com.coldblue.data.repository.manda.MandaDetailRepository
import com.coldblue.data.repository.manda.MandaKeyRepository
import com.coldblue.data.repository.todo.MandaTodoRepository
import javax.inject.Inject

class DeleteMandaAllUseCase @Inject constructor(
    private val mandaKeyRepository: MandaKeyRepository,
    private val mandaDetailRepository: MandaDetailRepository,
    private val mandaTodoRepository: MandaTodoRepository,
) {
    suspend operator fun invoke() {
        mandaKeyRepository.deleteAllMandaDetail()
        mandaDetailRepository.deleteAllMandaDetail()
        mandaTodoRepository.deleteAllMandaTodo()
    }
}