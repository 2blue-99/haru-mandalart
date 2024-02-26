package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaRepo
import com.coldblue.model.MandaKey
import javax.inject.Inject

class UpdateMandaKeyUseCase @Inject constructor(
    private val mandaRepo: MandaRepo
) {
    suspend operator fun invoke(data: MandaKey){
        mandaRepo.upsertMandaKeysRepository(listOf(data))
    }
}