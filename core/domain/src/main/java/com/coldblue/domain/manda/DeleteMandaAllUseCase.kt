package com.coldblue.domain.manda

import com.coldblue.data.repository.manda.MandaDetailRepository
import com.coldblue.data.repository.manda.MandaKeyRepository
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