package com.coldblue.domain.manda

import com.coldblue.data.repository.manda.MandaDetailRepository
import javax.inject.Inject

class DeleteMandaDetailUseCase @Inject constructor(
    private val mandaDetailRepository: MandaDetailRepository
) {
    suspend operator fun invoke(id: Int){
        mandaDetailRepository.deleteMandaDetail(listOf(id))
    }
}