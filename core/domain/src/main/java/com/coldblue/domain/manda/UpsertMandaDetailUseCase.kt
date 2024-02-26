package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaDetailRepository
import com.coldblue.model.MandaDetail
import javax.inject.Inject

class UpsertMandaDetailUseCase @Inject constructor(
    private val mandaDetailRepository: MandaDetailRepository
) {
    suspend operator fun invoke(data:MandaDetail){
        mandaDetailRepository.upsertMandaDetails(listOf(data))
    }
}