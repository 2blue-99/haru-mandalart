package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaKeyRepository
import com.coldblue.model.MandaKey
import javax.inject.Inject

class DeleteMandaDetailUseCase @Inject constructor(
    private val mandaKeyRepository: MandaKeyRepository
) {
    suspend operator fun invoke(id: Int){
        mandaKeyRepository.deleteMandaKeys(listOf(id))
    }
}