package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaRepo
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import javax.inject.Inject

class DeleteMandaDetailUseCase @Inject constructor(
    private val mandaRepo: MandaRepo
) {
    suspend operator fun invoke(data:MandaDetail){
        mandaRepo.upsertMandaDetailsRepository(listOf(data))
    }
}