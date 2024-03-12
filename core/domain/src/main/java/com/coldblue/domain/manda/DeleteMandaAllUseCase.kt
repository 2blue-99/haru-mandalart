package com.coldblue.domain.manda

import com.coldblue.data.repository.MandaDetailRepository
import com.coldblue.data.repository.MandaKeyRepository
import javax.inject.Inject

class DeleteMandaAllUseCase @Inject constructor(
    private val mandaKeyRepository: MandaKeyRepository,
    private val mandaDetailRepository: MandaDetailRepository
) {
    suspend operator fun invoke(){
        mandaKeyRepository.deleteAllMandaDetail()
        mandaDetailRepository.deleteAllMandaDetail()
    }
}