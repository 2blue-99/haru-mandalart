package com.coldblue.domain.manda

import com.coldblue.data.repository.MandaKeyRepository
import com.coldblue.model.MandaKey
import javax.inject.Inject

class UpsertMandaKeyUseCase @Inject constructor(
    private val mandaKeyRepository: MandaKeyRepository
) {
    suspend operator fun invoke(mandaKey: MandaKey){
        mandaKeyRepository.upsertMandaKeys(listOf(mandaKey))
    }
}