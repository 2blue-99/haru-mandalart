package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaDetailRepository
import com.coldblue.data.repo.MandaKeyRepository
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import javax.inject.Inject

class DeleteMandaDetailUseCase @Inject constructor(
    private val mandaDetailRepository: MandaDetailRepository
) {
    suspend operator fun invoke(id: Int){
        mandaDetailRepository.deleteMandaDetail(listOf(id))
    }
}